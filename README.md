# Static File Server Boilerplate Java Spark

Ubicación de los archivos estáticos:

    src/main/resources/public

Crear war usando Maven:

    $ mvn package

Cargar dependencias bower y npm:

    $ bower install && npm install

Ejecutar Main Class usando Maven:

    $ mvn clean && mvn install && mvn exec:java -Dexec.mainClass="configs.App"

### Migraciones

Migraciones con DBMATE - ubicaciones:

    $ dbmate -d "db/migrations" -e "ACCESS" new <<nombre_de_migracion>>
    $ dbmate -d "db/migrations" -e "ACCESS" up

### BDD con cucumber

Instalación de dependencias:

	$ gem install cucumber
	$ bundler install

Ejecutar pruebas:

	$ cucumber 
    $ cucumber features/<file_name>.feature

--- 

Fuentes

+ https://www.mkyong.com/maven/how-to-create-a-web-application-project-with-maven/
+ https://stackoverflow.com/questions/9846046/run-main-class-of-maven-project
+ http://sparkjava.com/