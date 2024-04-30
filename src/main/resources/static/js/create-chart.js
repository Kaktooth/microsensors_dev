const currentUrl = window.location.href;
var key = $.ajax({
    url: '/dashboard/request-key',
    type: "GET",
    dataType: "json",
    beforeSend: function () {
        $('#loader').css("visibility", "visible");
    },

    success: function (data) {
        return data;
    },
    complete: function () {
        $('#loader').css("visibility", "hidden");
    },
    error: function (e) {
        console.log(e.message);
    }
});

var dataPath = "";
var sensorPath = "";

var chart1Handler;

async function createChart() {
    await key.done(function () {
        dataPath = currentUrl.replace("/chart", "/csv/".concat(key.responseJSON)).replace("?", "");
        sensorPath = currentUrl.replace("/chart", "/json/".concat(key.responseJSON)).replace("?", "");
    }).fail(function () {
        console.log('failed to get key');
    });

    var table = [];
    var x = [];
    var y = new Map;
    await d3.csv(dataPath, data => table.push(data));
    var labels = Object.keys(table[0]);
    var dataLabels = labels.splice(1);
    var date = table[0][labels[0]];
    var removeIndex = date.indexOf("T")
    date = date.slice(0, removeIndex);
    table.forEach(row => {
        var time = row[labels[0]];
        var removeIndexStart = time.indexOf("T") + 1;
        var removeIndexEnd = time.indexOf(".");
        time = time.slice(removeIndexStart, removeIndexEnd);
        x.push(time);

        dataLabels.forEach(label => {
            var value = [];
            if (y.has(label)) {
                value = y.get(label);
            }
            value.push(parseFloat(row[label]))
            y.set(label, value);
        })
    })

    var i = 0;
    var chartData = {
        labels: x,
        datasets: []
    }
    dataLabels.forEach((sensorDataLabel) => {

        const dataChart = {
            label: sensorDataLabel,
            data: y.get(sensorDataLabel),
            borderColor: color(i),
            fill: false,
            cubicInterpolationMode: 'monotone',
            tension: 0.4
        }
        i++;
        chartData.datasets.push(dataChart);
    })
    if (chart1Handler == null) {
        chart1Handler = new Chart('chart', {
            type: 'line',
            data: chartData,
            options: {
                plugins: {
                    title: {
                        display: true,
                        text: 'Sensor Data Chart'
                    },
                    subtitle: {
                        display: true,
                        text: 'Current date: ' + date.toString(),
                        color: 'blue',
                        font: {
                            size: 12,
                            family: 'tahoma',
                            weight: 'normal',
                            style: 'italic'
                        },
                        padding: {
                            bottom: 10,
                        }
                    },
                },
                scales: {
                    x: {
                        display: true,
                        title: {
                            display: true
                        }
                    },
                    y: {
                        display: true,
                        title: {
                            display: true,
                            text: 'Value'
                        }
                    }
                }
            }

        });
        chart1Handler.options.animation = false;
        chart1Handler.options.animations.colors = false;
        chart1Handler.options.animations.x = false;
        chart1Handler.options.transitions.active.animation.duration = 0;
    } else {
        chart1Handler.data = chartData;
        chart1Handler.update();
    }
}

const COLORS = [
    '#4dc9f6',
    '#f67019',
    '#f53794',
    '#537bc4',
    '#acc236',
    '#166a8f',
    '#00a950',
    '#58595b',
    '#8549ba'
];

function color(index) {
    return COLORS[index % COLORS.length];
}