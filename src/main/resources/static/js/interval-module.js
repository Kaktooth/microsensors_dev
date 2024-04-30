
var requestKey = $.ajax({
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

setInterval(function () {
    const queryString = window.location.pathname;
    if (queryString.includes("/dashboard/sensor") && !queryString.includes("/chart")
        && !queryString.includes("csv") && !queryString.includes("json")) {
        data().then();
    }
    if (queryString.includes("/dashboard/sensor") && queryString.includes("/chart")) {
        createChart().then();
    }
}, 200);

async function data() {
    var sensor = {};
    const currentUrl = window.location.href;
    await requestKey.done(function () {
        sensorPath = currentUrl.replace("?", "")
            .concat("/json/".concat(requestKey.responseJSON));
    }).fail(function () {
        console.log('failed to get key');
    });
    await fetch(sensorPath, {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
        },
    })
        .then(async response => sensor = await response.json());

    if (document.getElementById('sensorMessages') != null) {
        let sensorMessages = document.getElementById('sensorMessages');
        sensorMessages.scrollTop = sensorMessages.scrollHeight;
        let sensorData = document.getElementById('sensorData');
        sensorData.scrollTop = sensorData.scrollHeight;
        sensorData.value = sensor.sensorDataString;
        sensorMessages.value = sensor.sensorMessagesString;
    }
}