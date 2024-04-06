@set file=target/othello.zip
call mvn clean package dependency:copy-dependencies -DincludeScope=runtime -DskipTests -DoutputDirectory=target/lib
powershell -NoLogo -NoProfile Compress-Archive -Path "./target/lib,./target/*.jar" -Force %file%
@echo fichier zip %file% cree