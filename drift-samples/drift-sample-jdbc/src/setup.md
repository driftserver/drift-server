run standalone H2 server:

eclipse: 

run configurations > java application > new
main tab > project: drift-sample-jdbc
main tab > Main class: org.h2.tools.Server
arguments tab > VM arguments: -Durl=jdbc:h2:tcp://localhost/./test

