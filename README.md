# Spring Boot application

This is an simple backend using Java / Maven / Spring boot with postgresql database  for an book application that can be used to add, delete and update books .

## Dependencies

  - Java
  - Maven
  - Postgresql
  - spring boot
  

## Configure the project

  we use spring initializr to configure our application , maven project java 17 as language spring boot version (3.0.2), 
  and configure project metadata then choose packaging Jar.
  And finally we add  necessary dependencies :
      * Spring web
      * Spring Boot DevTools
      * lambok
      * PostgreSQLDriver
      * Spring Data JPA
      * Validation
      
      ### In pom.xml:
      
          <dependencies>
              <dependency>
                  <groupId>org.springframework.boot</groupId>
                  <artifactId>spring-boot-starter-data-jpa</artifactId>
              </dependency>
              <dependency>
                  <groupId>org.springframework.boot</groupId>
                  <artifactId>spring-boot-starter-web</artifactId>
              </dependency>
              <dependency>
                  <groupId>org.springframework.boot</groupId>
                  <artifactId>spring-boot-starter-validation</artifactId>
                  <version>3.0.2</version>
              </dependency>
              <dependency>
                  <groupId>org.springframework.boot</groupId>
                  <artifactId>spring-boot-devtools</artifactId>
                  <scope>runtime</scope>
                  <optional>true</optional>
              </dependency>
              <dependency>
                  <groupId>org.postgresql</groupId>
                  <artifactId>postgresql</artifactId>
                  <scope>runtime</scope>
              </dependency>
              <dependency>
                  <groupId>org.projectlombok</groupId>
                  <artifactId>lombok</artifactId>
                  <optional>true</optional>
              </dependency>
              <dependency>
                  <groupId>org.springframework.boot</groupId>
                  <artifactId>spring-boot-starter-test</artifactId>
                  <scope>test</scope>
              </dependency>
         </dependencies>
      
 ## About Service
 
   The service is just a simple Book review REST service. It uses an relational database (PostgreSQL) to store the data.
   If your database connection properties work, you can call some REST endpoints defined in com.examenspring.examendi.controller.LivreController on port 8080.
   
   Here are some endpoints you can call:
   
      | Méthodes          |     URLs                    |         Actions |
      |-------------------|-----------------------------|-----------------|
      |     POST          |       /api/livres           | 	create a new book
      |     GET           |	      /api/livres           |	  collect all the books
      |     GET           |       /api/livres/:id       |	  retrieve a book by :id
      |     PUT           |	      /api/livres/:id       |	  update a book by :id
      |     DELETE        | 	    /api/livres/:id       |	  delete a book by :id
      |     DELETE        |	      /api/livres           |	  delete all books
      |     GET 	        | /api/livres?titre=[mot-clé] |	  find all books that contain the title
      
      
 ## Running the project with POSTGRESQL
 
   Here is what you would do to back the services with POSTGRESQL, for example:
   
   ### In pom.xml add:
    
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <scope>runtime</scope>
        </dependency>
        
   ### Append this to the end of application.yml: 
   
      spring:
        datasource:
           url: jdbc:postgresql://<your_postgresql_host_or_ip>/bootexample
           username: <your_postgresql_username>
           password: <your_postgresql_password>
           driver-class-name: org.postgresql.Driver
        jpa:
          show-sql: true
          hibernate:
             ddl-auto: create-drop
          properties:
             hibernate:
                format_sql: true
          database: postgresql
          database-platform: org.hibernate.dialect.PostgreSQLDialect
          
          
  ## RoadMap of Application:
    - Create an Entity : Livre
    - create Livre repository extends from JpaRepositoryaith args Livre.class and id Type -> Long
    - Livre Service with all api services in this application: 
            
            
    - livre controller with all application requests:
         Get Requests:
            * getAllLivre()
            * getLivreById(Long id)
            * getLivreByTitle(String title)
            
         Delete Requests:
            * deleteById(Long id)
            * deleteAll()
            
         Post request:
            * addLivre(Livre livre)
            * updateLivre(Long id, Livre livre)
            
            
           
  
  ### Application's exception
   *  Create an abstract class called ApiBaseException who extends from RuntimeException where we create an abstract Method called getStatusCode() return an HttpStatus.
                            
                            public abstract class ApiBaseException extends RuntimeException{

                                 public ApiBaseException(String message) {
                                     super(message);
                                 }

                                 public abstract HttpStatus getStatusCode();
                             }
      
       *create some exceptions who extends from ApiBaseException : 
             ConflictException : for verification data before we inserted in database
          
                            public class ConflictException extends ApiBaseException{
                                public ConflictException(String message) {
                                      super(message);
                                }

                                @Override
                                public HttpStatus getStatusCode() {
                                    return HttpStatus.CONFLICT;
                                }
                            }
                  
         RessourceNotFoundException :
                              
                              public class RessourceNotFoundException extends ApiBaseException {
                              public RessourceNotFoundException(String message){
                                  super(message);
                              }

                              @Override
                              public HttpStatus getStatusCode(){
                                  return HttpStatus.NOT_FOUND;
                              }
                          }
         
         an Global exception with Annotation `@ExceptionHandler(ApiBaseException.class)` , Overriding handleMethodArgumentNotValid for validation of insertion an                updation of data, where we define Entity with spring validation (@NotNull, @Size, @Valid)
                              
                              @ExceptionHandler(ApiBaseException.class)
                              public ResponseEntity<ErrorDetails> handleApiException(ApiBaseException ex, WebRequest request){
                                 ErrorDetails error = new ErrorDetails(ex.getMessage(), request.getDescription(false));
                                 return new ResponseEntity<>(error, ex.getStatusCode());
                              }
                  
                              @Override
                              protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                                              HttpHeaders headers,
                                                                                              HttpStatusCode status,
                                                                                              WebRequest request) {
        
                              ValidationError validationErrors = new ValidationError();
                              validationErrors.setUri(request.getDescription(false));

                              List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

                              for (FieldError f: fieldErrors) {
                                  validationErrors.addError(f.getDefaultMessage());
                              }

        

                              return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
                          }
                          
                          
        #### Customize Error:
        
            1/
        
              public class ErrorDetails {
                  private String message;
                  private String uri;
                  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
                  private Date timestamp;

                  public ErrorDetails() {
                      this.timestamp = new Date();
                  }

                  public ErrorDetails(String message, String uri) {
                      this();
                      this.message = message;
                      this.uri = uri;
                  }

                 /*
                    Getters & Setters
                 */   
              }
              
              
              2/
              
              
              public class ValidationError {

                  private List<String> errors;
                  private String uri;
                  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
                  private Date timesTamp;

                  public ValidationError() {
                      this.timesTamp = new Date();
                      this.errors = new ArrayList<>();
                  }

                  public void addError(String error){
                      this.errors.add(error);
                  }

                  /*
                    Getters & Setters
                 */  
              }

       
