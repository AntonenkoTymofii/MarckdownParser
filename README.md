# MarckdownParser

#### Виконав Антоненко Тимофій, студент групи ІМ-21

## Короткий опис застосунку, що він робить

Консольний застосунок створений на мові Java. 
Застосунок має модульний підхід до вирішення задачі. 
Також використовується наслідування та дотримується інкапсуляція.
Використовується для перетворення файлів .markdown в файли .html 
з збереженням розмітки та форматування тексту.
Застосунок виконує всі поставлені вимоги та проходить перевірки на різні види помилок,
які описані в методичних вказівках до лабораторної роботи №1.

## Інструкція, як зібрати та запустити проект

1. Якщо у вас не встановлений JDK його потрібно встановити за ось цим посиланням: 
<a name="jdk" href="https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html">Встановити JDK</a>
2. Вивантажити репозиторій на локальний комп'ютер.
3. Запустити термінал по такому шляху: .\MarckdownParser\lab1\src
4. Запустити по черзі команди: "javac Main.java" та "java Main TEST.md --out test.html"
5. Команда "javac Main.java" збирає проєкт 
6. Команда "java Main TEST.md --out test.html" запускає зібраний проєкт
7. Якщо ваші файли TEST.md та test.html знаходяться не в папці src,
то треба писати повний шлях до них

## Інструкція до використання проекту

Щоб використати проєкт вам треба виконати інструкції описані вище. 
В другій команді вказати параметрами файл для считування та запису. 
Якщо файл для запису не буде вказаний, то код на html буде виведений в терміналі. 
Програма автоматизована після запуску команди "java Main TEST.md --out test.html" в 
терміналі буде виведено повідомлення: "HTML content saved to 'test.html'". 
Після чого ви можете перейти до свого файлу test.html та продивитись його 
наповнення та відкрити його у браузері.