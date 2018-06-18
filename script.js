
 
// READ records
function readRecords() {
    $.get("readRecords.php", {}, function (data, status) {
        $(".records_content").html(data);
    });
}
 
 
function DeletePlace(place_id) {
    var conf = confirm("Are you sure, do you really want to delete the place?");
    if (conf == true) {
        $.post("deletePlace.php", {
                place_id: place_id
            },
            function (data, status) {
                // reload Places by using readRecords();
                readRecords();
				window.location.reload(true); 
            }
        );
    }
}
 
function GetPlaceDetails(place_id) {
    // Add Place ID to the hidden field for furture usage
    $("#hidden_place_id").val(place_id);
    $.post("readPlaceDetails.php", {
            place_id: place_id
        },
        function (data, status) {
            // PARSE json data
            var place = JSON.parse(data);
            // Assing existing values to the modal popup fields
            $("#update_name").val(place.name);
            $("#update_description").val(place.description);
			$("#update_image").val(place.pfimage);  //img
            //$("#update_email").val(place.email);
        }
    );
    // Open modal popup
    $("#update_place_modal").modal("show");
}
 
function UpdatePlaceDetails() {
    // get values
    var name = $("#update_name").val();
    var description = $("#update_description").val();
    //var email = $("#update_email").val();	
	var pfimage = $("#update_image").val();  //img
 
    // get hidden field value
    var place_id = $("#hidden_place_id").val();
 
    // Update the details by requesting to the server using ajax
    $.post("updatePlaceDetails.php", {
            place_id: place_id,
            name: name,
            description: description,
			pfimage: pfimage  //img
            //email: email
        },
        function (data, status) {
            // hide modal popup
            $("#update_place_modal").modal("hide");
            // reload Places by using readRecords();
            readRecords();
			window.location.reload(true); 
        }
    );
}
 
$(document).ready(function () {
    // READ recods on page load
    readRecords(); // calling function
});