<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="x-ua-compatible" content="ie=edge">

		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
		<link href="https://fonts.googleapis.com/css?family=Open+Sans|Ubuntu" rel="stylesheet">
		<link rel="stylesheet" href="passcheck.css" type="text/css">
		<title>Password Checker | Kisoki</title>
		<meta name="description" content="Don't get hacked. Find out if your password has been compromised. Our app cross-references passwords with a compromised password database.">
		<meta name="author" content="Kisoki Information Systems, LLC">

		<meta name="mobile-web-app-capable" content="yes">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="shortcut icon" href="/passcheck/src/main/webapp/favicon.ico">
	</head>
	<body>
		<div class="inside-container">
		<!--This is pretty straightforward-->
			<section class="hero" id="hero">
				<p class="tagline">Password Checker</p>
			</section>
			<!--Gets user input and calls main function once submitted-->
			<section class="section-form-wrapper">
				<div class="inset-form" id="contact">
					<div class="about">
						<div class="flex-text">
							<h2>Enter a Password</h2>
						</div>
					</div>
						<div class="flex-form" id="imessage">
							<form onsubmit="event.preventDefault(); passSubmit();" class="formContact" id="passInput">
								<input class="infld" type="password" name="password" id="password" />
								<input class="inbtn"  id="mySubmit" type="submit" value="Run Checker" />
							</form>
						</div>	
				</div>
			</section>
			<!--The loader animation goes here-->
			<section class="section-form-wrapper">
				<div class="inset-form">
					<div class="lds-css ng-scope" id="loadingIcon" style="display:none">
						<div class="lds-wave" style="height:100%;display: block;margin-left: 40%;"><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div><div></div></div>
						<style>
							@keyframes lds-wave{0%,100%{top:54px}50%{top:134px}}@-webkit-keyframes lds-wave{0%,100%{top:54px}50%{top:134px}}.lds-wave{position:relative}.lds-wave div{width:12px;height:12px;position:absolute;border-radius:50%;-webkit-animation:lds-wave 1s cubic-bezier(.5,0,.5,1) infinite;animation:lds-wave 1s cubic-bezier(.5,0,.5,1) infinite}.lds-wave div:nth-child(1){left:22px;-webkit-animation-delay:0s;animation-delay:0s;background:#1d3f72}.lds-wave div:nth-child(2){left:36px;-webkit-animation-delay:-83.333333333333ms;animation-delay:-83.333333333333ms;background:#5699d2}.lds-wave div:nth-child(3){left:50px;-webkit-animation-delay:-.166666666666667s;animation-delay:-.166666666666667s;background:#d8ebf9}.lds-wave div:nth-child(4){left:64px;-webkit-animation-delay:-.25s;animation-delay:-.25s;background:#71c2cc}.lds-wave div:nth-child(5){left:78px;-webkit-animation-delay:-.333333333333333s;animation-delay:-.333333333333333s;background:#1d3f72}.lds-wave div:nth-child(6){left:92px;-webkit-animation-delay:-.416666666666667s;animation-delay:-.416666666666667s;background:#5699d2}.lds-wave div:nth-child(7){left:106px;-webkit-animation-delay:-.5s;animation-delay:-.5s;background:#d8ebf9}.lds-wave div:nth-child(8){left:120px;-webkit-animation-delay:-.583333333333333s;animation-delay:-.583333333333333s;background:#71c2cc}.lds-wave div:nth-child(9){left:134px;-webkit-animation-delay:-.666666666666667s;animation-delay:-.666666666666667s;background:#1d3f72}.lds-wave div:nth-child(10){left:148px;-webkit-animation-delay:-.75s;animation-delay:-.75s;background:#5699d2}.lds-wave div:nth-child(11){left:162px;-webkit-animation-delay:-.833333333333333s;animation-delay:-.833333333333333s;background:#d8ebf9}.lds-wave div:nth-child(12){left:176px;-webkit-animation-delay:-.916666666666667s;animation-delay:-.916666666666667s;background:#71c2cc}.lds-wave{width:100px!important;height:100px!important;-webkit-transform:translate(-50px,-50px) scale(.5) translate(50px,50px);transform:translate(-50px,-50px) scale(.5) translate(50px,50px)}
						</style>
					</div>
				</div>
			</section>
			<section class="section-content-wrapper">
				<div class="inset">
					<article>
						<div class="content-wrap" id="messageGood" style="display: none;">
							<div class="alert alert-success" role="alert">
								<strong>No Match Found: Your password is safe!</strong>
							</div>
						</div>
						<div class="content-wrap" id="messageBad" style="display: none;">
							<div class="alert alert-danger" role="alert">
								<strong>Match Found: You should change your password!</strong>
							</div>
						</div>
					</article>
				</div>
			</section>
			<!--Where we display info about the app-->
			<section class="about">
				<div class="flex-text-report">
					<h2>About This App</h2>
						<p style="font-family: 'Open Sans', sans-serif;">At Kisoki, we take security very seriously. This app was put together because thousands of passwords are cracked every day, and are shared in password lists around the internet. We belive that if your passwords have been compromised you have the right to know about it.</p>

						<img src="" style="">					
				</div>
			</section>
			<!--This is the footer-->
			<footer class="footer">
				<p class="text-muted">&copy;2017 Kisoki Information Systems, LLC</p>
				<p class="text-muted"><a href="socialm@kisoki.com">socialm@kisoki.com</a></p>
				<p class="text-muted"><a href="#">Git Hub</a></p>
			</footer>
	</div>
			<!--This is the js-->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
		<script>
			function passSubmit() {
                                $('#messageBad').css("display", "none");
                                $('#messageGood').css("display", "none");
				$('#loadingIcon').show();
				var sendValue = $.post('/CheckPass', {
					password: $('#password').val()
				});
				sendValue.done(function (data) {
		
					var output;
					if (data.passfound) {
					  $('#messageBad').css("display", "block");
					}
					else {
                                          $('#messageGood').css("display", "block");
					}
					$('#loadingIcon').hide();
				});
			}
		</script>
		<!--script>
			function passSubmit() {
				$('#loadingIcon').show();
				//var sendValue = $.post('/CheckPass', {
				var sendValue = $.post('https://passcheck-kisoki-com.appspot.com/CheckPass', {
					password: $('#password').val()
				});
				sendValue.done(function (data) {
					var output = "";
					if (data.passfound) {
						output = "<font color=\"red\">Password Found!</font>";
					}
					else {
						output = "<font color=\"green\">Password Not Found!</font>";
					}
					$('#checkResult').html(output);
					$('#loadingIcon').hide();
				});
			}
		</script-->
		
		<!--script>
			function passSubmit() {
				$('#loadingIcon').show();
				var sendValue = $.post('/CheckPass', {
					password: $('#password').val()
				});
				sendValue.done(function (data) {
					var resultGood = $('<div />').append(data).find('#messageGood').html();
					var resultBad = $('<div />').append(data).find('#messageBad').html();
					var output;
					if (data.passfound) {
					  output = resultBad;
					}
					else {
						output = resultGood;
					}
				$('#loadingIcon').hide();
				});
			}
		</script-->
	</body>
</html>