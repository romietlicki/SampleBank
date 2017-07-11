<%@ page language="java" contentType="text/html;"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Sample Bank - Home</title>
<link href="resources/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="resources/scripts/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="resources/scripts/jquery.maskedinput.js"></script>
<script type="text/javascript" src="resources/scripts/masks.js"></script>
</head>
<body>
	<div class="sb-home-container">

		<div class="sb-top-logo">
			<div class="sb-logo">
				<img src="resources/images/bank_logo_small.png" alt="Sample Bank" />
			</div>
		</div>
		<div class="sb-banner">
			<a class="sb-no-formatted-link"
				href="http://localhost:8080/samplebank/home"> SAMPLE-BANK S.A </a>
		</div>

		<div class="sb-login-info">
			<span id="sb-username"> Welcome, <c:out
					value="${sessionScope.username}" /> (<a href="logout">logout</a>)
			</span>
		</div>

		<hr class="top-rule" />

		<div class="sb-admin-content">