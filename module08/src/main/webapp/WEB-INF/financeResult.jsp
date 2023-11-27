<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Результат расчёта доходности</title>
    <link href="style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
    <header>
      <h1>Результат</h1>
    </header>
    <div>
      <p>Итоговая сумма <%=request.getAttribute("total")%> рублей</p>
    </div>
</body>
</html>