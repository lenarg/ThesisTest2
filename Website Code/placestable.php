<!-- user file-->

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title></title>
 
    <!-- Bootstrap CSS File  -->
    <link rel="stylesheet" type="text/css" href="bootstrap-3.3.5-dist/css/bootstrap.css"/>
</head>
<body>
 
<!-- Content Section -->
<div class="container">
    <div class="row">
        <div class="col-md-2">
        </div>
    </div>
    <div class="row">
        <div class="col-md-2">
            <div class="pull-right">
                <!--<button class="btn btn-success" data-toggle="modal" data-target="#add_new_record_modal">Add New Record</button>-->
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-2">
            
 
            <div class="records_content"></div>
        </div>
    </div>
</div>
<!-- /Content Section -->
 
 
<!-- Bootstrap Modals -->

<!-- Modal - Update Place details -->
<div class="modal fade" id="update_place_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Update</h4>
            </div>
            <div class="modal-body">
 
                <div class="form-group">
                    <label for="update_name">Name</label>
                    <input type="text" id="update_name" placeholder="Name" class="form-control"/>
                </div>
 
                <div class="form-group">
                    <label for="update_description">Description</label>
                    <input type="text" id="update_description" placeholder="Description" class="form-control"/>
                </div>
 
                <!--<div class="form-group">
                    <label for="update_email">Email Address</label>
                    <input type="text" id="update_email" placeholder="Email Address" class="form-control"/>
                </div>-->
 
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-primary" onclick="UpdatePlaceDetails()" >Save Changes</button>
                <input type="hidden" id="hidden_place_id">
            </div>
        </div>
    </div>
</div>
<!-- // Modal -->
 
<!-- Jquery JS file -->
<script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
 
<!-- Bootstrap JS file -->
<script type="text/javascript" src="bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
 
<!-- Custom JS file -->
<script type="text/javascript" src="script.js"></script>



</body>
</html>