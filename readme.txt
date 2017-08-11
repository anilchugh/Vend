Running the program

1. Place coin-inventory.properties from src/main/resources/ into C:/temp drive or similar location
2. Check application.properties entry for coin.inventory.property.file.location is correctly pointing to above location.
3. Build the program
mvn clean install
4. Run program using command from target folder containing jar file  
java -jar Vend-0.0.1-SNAPSHOT.jar 1500