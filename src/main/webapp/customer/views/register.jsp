<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Customer Login</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>

<body>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-4">
                <h2 class="text-center">Customer Sign up</h2>
				<form action="${pageContext.request.contextPath}/customer/register" method="post">
				    <div class="form-group">
				        <label for="name">Full Name</label>
				        <input type="text" class="form-control" id="name" name="name" required>
				    </div>
				    <div class="form-group">
				        <label for="email">Email</label>
				        <input type="email" class="form-control" id="email" name="email" required>
				    </div>
				    <div class="form-group">
				        <label for="phonenumber">Phone Number</label>
				        <input type="text" class="form-control" id="phonenumber" name="phonenumber" required>
				    </div>
				    <div class="form-group">
				        <label for="password">Password</label>
				        <input type="password" class="form-control" id="password" name="password" required>
				    </div>
				    <button type="submit" class="btn btn-primary btn-block mt-3">Create Account</button>
				</form>
            </div>
        </div>
    </div>
</body>

</html>