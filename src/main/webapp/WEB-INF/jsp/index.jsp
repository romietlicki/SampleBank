<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
	<head>
		<title>Welcome to Sample Bank</title>
		<link href="resources/css/style.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
		<div class="sb-index-container">
			<div class="sb-logo">
				<img src="resources/images/bank_logo.png" alt="Sample Bank" />
			</div>
			<div class="sb-title">
				<h1>Sample Bank</h1>
			</div>
			<div class="sb-login-area">
				<form id="sb-form-login" action="login" method="post">
					<table class="sb-center-table">
						<tr>
							<td>
								<label class="sb-white-label">Username: </label>
							</td>
							<td>
								<form:input type="text" name="username" path="login.username" maxlength="15" />
							</td>
						</tr>
						<tr>
							<td>
								<label class="sb-white-label">Password: </label>
							</td>
							<td>
								<form:input type="password" name="password" path="login.password" maxlength="15" />
							</td>
						</tr>
						<tr>
							<td colspan="2" align="right">
								<input class="sb-button" type="submit" value="Login" />
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div class="sb-footer">
				Copyright &reg; SampleBank 2015 - All Rights Reserved
			</div>
		</div>
	</body>
</html>