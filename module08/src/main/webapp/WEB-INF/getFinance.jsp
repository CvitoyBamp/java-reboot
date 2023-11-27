<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Калькулятор доходности вклада</title>
    <link href="style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
    <header>
        <h1>Калькулятор доходности вклада</h1>
    </header>
    <section>
        <form action="/finance"
              method="post"
              name="postFinance">
            <table>
                <tr>
                    <td><label for="amount">Сумма на момент открытия</label></td>
                    <td class="input"><input id="amount" type="text" required name="amount"></td>
                </tr>
                <tr>
                    <td><label for="proc">Процетная ставка</label></td>
                    <td class="input"><input id="proc" type="text" required name="proc"></td>
                </tr>
                <tr>
                    <td><label for="year">Количество лет</label></td>
                    <td class="input"><input id="year" type="text" required name="year"></td>
                </tr>
                <tr>
                    <td><input class="calc" type=submit value="Посчитать"></td>
                </tr>
            </table>
        </form>
    </section>
</body>
</html>