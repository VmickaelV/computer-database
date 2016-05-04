$(function () {

    // Initialization of Internationalization
    jQuery.i18n.properties({
        name: 'js/localization/messages',
        cache : true,
        mode : 'map',
        language : $("html").attr("lang") || navigator.language
    });

    // Validation du formulaire
    var inputIntroducedDate = $("#introducedDate");
    var inputDiscontinuedDate = $("#discontinuedDate");

    jQuery.validator.addMethod("introducedDateValid", function(value, element, param) {
        if (inputIntroducedDate.val() == "" || inputDiscontinuedDate.val() == "") {
            return true;
        }

        var currentDate = Date.now();
        var dateIntroduced = inputIntroducedDate.datepicker('getDate');
        var dateDiscontinued = inputDiscontinuedDate.datepicker('getDate');
        if (dateIntroduced == null || dateDiscontinued == null) {
            return true;
        }
        return dateIntroduced <= currentDate && dateDiscontinued <= currentDate && dateIntroduced < dateDiscontinued;
    }, jQuery.i18n.prop("error.invalideDateOrder"));

    $("form").validate({
        rules: {
            introducedDate: {
                date: true,
                introducedDateValid : true
            },
            discontinuedDate: {
                date: true,
                introducedDateValid : true
            }
        },
        messages: {
            name: {
                required: jQuery.i18n.prop("lbl.error.computerNameCompulsory")
            }
        },
        errorClass: "help-block",
        highlight: function(element, errorClass, validClass) {
            $(element).parent().addClass("has-error");
        },
        unhighlight: function(element, errorClass, validClass) {
            $(element).parent().removeClass("has-error");
            console.log(element, errorClass, validClass);
        }

    });

    inputIntroducedDate.datepicker({
        changeMonth: true,
        changeYear: true,
        maxDate: "+0d"
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
        maxDate: "+0d"
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