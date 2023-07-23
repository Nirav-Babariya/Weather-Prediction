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
  left:20%;
  top:20%;
}
</style>
</head>

<body>
    <font color="red">${errorMessage}</font>
    <h1> Type the city name you want to see the weather details: </h1>
    <form method="post">
        City : <input type="text" id="city" name="city" />
        <input type="submit"/>
    </form>
</body>

</html>