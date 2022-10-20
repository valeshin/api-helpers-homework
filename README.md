# ДЗ #3: Citrus HTTP&SOAP tests&helpers
___

В репозитории реализованы тесты с применением хелперов, реализованных на Citrus через AbstractTestBehavior. 
Из-за особенностей Citrus хелперы вызываются прямо из тестового метода.
1. TestHttp реализован для сервиса https://restful-booker.herokuapp.com/ - это бесплатный сервис для обучения 
тестированию API (20.10.2022 он почему-то не работает, надеюсь его поднимут :с). В тесте используются следующие хелперы:
   1. AuthBehavior - хелпер для авторизации
   2. CreateBookingBehavior - хелпер для создания бронирования
   3. DeleteBookingBehavior - хелпер для удаления тестового бронирования после завершения теста
2. TestSOAP реализованы для http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso 
wsdl тут http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso?WSDL
В тестах используется хелпер GetAllCountries для получения списка всех стран, данные из которого далее используются
для тестирования методов CountryName и CountryISOCode

&nbsp;
### Настройка окружения: 
Для запуска тестов из командной строки необходимо:
1. Установить Java, желательно не ниже 11 версии
2. Установить Maven, если он у вас еще не установлен, это странно, вот инструкция https://maven.apache.org/install.html

&nbsp;
### Запуск тестов:
* `mvn clean test` - запустить все тесты
* `mvn clean test -Dtest=TestClassName` - запуск конкретного тестового класса
* `mvn clean test -Dtest=TestClassName#testName` - запуск конкретного теста
