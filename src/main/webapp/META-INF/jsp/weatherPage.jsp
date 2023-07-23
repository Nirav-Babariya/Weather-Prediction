<html>

<head>
<title>Weather App</title>

<style>
body {
  background: url("/sky.jpg");
  background-size: cover;
  position:absolute;
  display:block;
  text-align:center;
  left:30%;
  top:30%;
}
</style>
</head>



<body>
    <h3>Here is the weather result for your city ${city}:</h3>
    <br>
    <b>High Temp:</b> ${weather.highTemp}
    <br>
    <b>Low Temp:</b> ${weather.lowTemp}
    <br>
    <b>Weather Advice:</b> ${weather.message}
    <br><br>

    <form action="welcome">
        <button>Go Back</button>
    </form>
</body>

</html>