# jOOQ-and-EclipseScout
Template Application with jOOQ and Eclipse Scout

## Start MS SQL Server with Docker
docker run -e 'ACCEPT_EULA=Y' -e 'MSSQL_SA_PASSWORD=<YourStrong!Passw0rd>' -e 'MSSQL_PID=Developer' --cap-add SYS_PTRACE -p 1433:1433 -d microsoft/mssql-server-linux