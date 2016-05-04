$(function () {
    $("form").validate({
        rules: {
            introducedDate: {
                date: true
            }
        }
    });

    var inputIntroducedDate = $("#introducedDate");
    var inputDiscontinuedDate = $("#discontinuedDate");

    inputIntroducedDate.datepicker({
        changeMonth: true,
        changeYear: true,
        maxDate: "+0d",
        buttonText: "Choose"
    }).change(function (event) {
        var startDate = inputIntroducedDate.datepicker('getDate');
        if (startDate != null) {
            startDate.setDate(startDate.getDate()+1);
            inputDiscontinuedDate.datepicker('option', 'minDate', startDate);
        } else {
            inputDiscontinuedDate.datepicker('option', 'minDate', null);
        }
    });

    inputDiscontinuedDate.datepicker({
        changeMonth: true,
        changeYear: true,
        maxDate: "+0d",
        buttonText: "Choose"
    }).change(function() {
        var startDate = inputDiscontinuedDate.datepicker('getDate');
        if (startDate != null) {
            startDate.setDate(startDate.getDate()-1);
            inputIntroducedDate.datepicker('option', 'maxDate', startDate);
        } else {
            inputIntroducedDate.datepicker('option', 'maxDate', "+0d");
        }
    });
});