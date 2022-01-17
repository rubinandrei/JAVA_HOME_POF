#  Home work
## OTUS Java Developer Professional course

1. Проект gradle с модульной структурой
    1. обавлен модуль `hw01-gradle`
    2. добавлена последняя версия зависимости
       `implementation 'com.google.guava:guava:31.0.1-jre'`
    3. Создан  модуль `hw01-gradle`
    4. добавлен класс *HelloOtus* c *main* методом
    5. запускать: ``./gradlew run``
    6. сборка: `` ./gradlew clean build jarAll`` в каталоге ./build/libs/ должен находиться толстый  jar *javaprof-1.0-SNAPSHOT.jar*
    7. запуск ``java -jar ./build/libs/javaprof-1.0-SNAPSHOT.jar ``