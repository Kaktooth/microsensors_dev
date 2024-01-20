$(document).ready(function () {
    $('.js-example-basic-multiple').select2({
            width: '100%',
            "z-index": 1100,
            maximumSelectionLength: 7
        }
    );
});

$(document).ready(function() {
    $("#sensors").select2({
        dropdownParent: $("#createSensor"),
        width: '100%'
    });
    $("#placements").select2({
        dropdownParent: $("#createSensor"),
        width: '100%'
    });
});
