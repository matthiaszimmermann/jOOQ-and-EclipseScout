package com.acme.application.server.sql;

//tag::createDB[]
//tag::organizationListing[]
public interface SQLs {
  //end::organizationListing[]

  String SELECT_TABLE_NAMES = ""
      + "SELECT   UPPER(tablename) "
      + "FROM     sys.systables "
      + "INTO     :result"; // <1>

  String ORGANIZATION_CREATE_TABLE = ""
      + "CREATE   TABLE ORGANIZATION "
      + "         (organization_id VARCHAR(64) NOT NULL CONSTRAINT ORGANIZATION_PK PRIMARY KEY,"
      + "          name VARCHAR(64), "
      + "          logo_url VARCHAR(512), "
      + "          url VARCHAR(64), "
      + "          street VARCHAR(64), "
      + "          city VARCHAR(64), "
      + "          country VARCHAR(2), "
      + "          phone VARCHAR(20), "
      + "          email VARCHAR(64), "
      + "          notes VARCHAR(1024)"
      + "         )";

  String PERSON_CREATE_TABLE = ""
      + "CREATE   TABLE PERSON "
      + "         (person_id VARCHAR(64) NOT NULL CONSTRAINT PERSON_PK PRIMARY KEY, "
      + "          first_name VARCHAR(64), "
      + "          last_name VARCHAR(64), "
      + "          picture_url VARCHAR(512), "
      + "          date_of_birth DATE, "
      + "          gender VARCHAR(1), "
      + "          street VARCHAR(64), "
      + "          city VARCHAR(64), "
      + "          country VARCHAR(2), "
      + "          phone VARCHAR(20), "
      + "          mobile VARCHAR(20), "
      + "          email VARCHAR(64), "
      + "          organization_id VARCHAR(64), "
      + "          position VARCHAR(512), "
      + "          phone_work VARCHAR(20), "
      + "          email_work VARCHAR(64), "
      + "          notes VARCHAR(1024), "
      + "          CONSTRAINT ORGANIZATION_FK FOREIGN KEY (organization_id) REFERENCES ORGANIZATION (organization_id)"
      + "         )";
// end::createDB[]

  String PERSON_LOOKUP = ""
      + "SELECT   person_id, "
      + "         CASE "
      + "           WHEN first_name IS null "
      + "            THEN last_name "
      + "           WHEN last_name IS null "
      + "            THEN first_name "
      + "           ELSE "
      + "            first_name || ' ' || last_name "
      + "         END "
      + "FROM     PERSON "
      + "WHERE    1 = 1 "
      + "<key>    AND person_id = :key</key> "
      + "<text>   AND (UPPER(first_name) LIKE UPPER('%'||:text||'%') "
      + "         OR UPPER(last_name) LIKE UPPER('%'||:text||'%')) "
      + "</text>"
      + "<all> </all>";

  //tag::lookupService[]
  String ORGANIZATION_LOOKUP = ""
      + "SELECT   organization_id, "
      + "         name "
      + "FROM     ORGANIZATION "
      + "WHERE    1 = 1 "
      + "<key>    AND organization_id = :key</key> " // <1>
      + "<text>   AND UPPER(name) LIKE UPPER(:text||'%') </text> " // <2>
      + "<all></all>"; // <3>
  //end::lookupService[]

  String AND_LIKE_CAUSE = "AND LOWER(%s) LIKE LOWER(:%s || '%%') ";

  //tag::organizationListing[]
  String ORGANIZATION_PAGE_SELECT = ""
      + "SELECT   organization_id, "
      + "         name, "
      + "         city, "
      + "         country, "
      + "         url "
      + "FROM     ORGANIZATION ";

  String ORGANIZATION_PAGE_DATA_SELECT_INTO = ""
      + "INTO     :{page.organizationId}, " // <1>
      + "         :{page.name}, "
      + "         :{page.city}, "
      + "         :{page.country}, "
      + "         :{page.homepage}";
  //end::organizationListing[]

  String ORGANIZATION_INSERT = ""
      + "INSERT   INTO "
      + "ORGANIZATION  (organization_id) "
      + "VALUES   (:organizationId)";

  String ORGANIZATION_SELECT = ""
      + "SELECT   name, "
      + "         logo_url, "
      + "         url, "
      + "         phone, "
      + "         email, "
      + "         street, "
      + "         city, "
      + "         country, "
      + "         notes "
      + "FROM     ORGANIZATION "
      + "WHERE    organization_id = :organizationId "
      + "INTO     :name, "
      + "         :picture.url, "
      + "         :homepage, "
      + "         :phone, "
      + "         :email, "
      + "         :addressBox.street, "
      + "         :addressBox.city, "
      + "         :addressBox.country, "
      + "         :notesBox.notes";

  String ORGANIZATION_UPDATE = ""
      + "UPDATE   ORGANIZATION "
      + "SET      name = :name, "
      + "         logo_url = :picture.url, "
      + "         url = :homepage, "
      + "         phone = :phone, "
      + "         email = :email, "
      + "         street = :addressBox.street, "
      + "         city = :addressBox.city, "
      + "         country = :addressBox.country, "
      + "         notes = :notesBox.notes "
      + "WHERE    organization_id = :organizationId";

  String PERSON_PAGE_SELECT = ""
      + "SELECT   person_id, "
      + "         first_name, "
      + "         last_name, "
      + "         city, "
      + "         country, "
      + "         phone, "
      + "         mobile, "
      + "         email, "
      + "         organization_id "
      + "FROM     PERSON ";

  String PERSON_PAGE_DATA_SELECT_INTO = ""
      + "INTO     :{page.personId}, "
      + "         :{page.firstName}, "
      + "         :{page.lastName}, "
      + "         :{page.city}, "
      + "         :{page.country}, "
      + "         :{page.phone}, "
      + "         :{page.mobile}, "
      + "         :{page.email}, "
      + "         :{page.organization}";

  String PERSON_INSERT = ""
      + "INSERT   INTO "
      + "PERSON  (person_id) "
      + "VALUES   (:personId)";

  String PERSON_SELECT = ""
      + "SELECT   first_name, "
      + "         last_name, "
      + "         picture_url, "
      + "         date_of_birth, "
      + "         gender, "
      + "         phone, "
      + "         mobile, "
      + "         email, "
      + "         street, "
      + "         city, "
      + "         country, "
      + "         position, "
      + "         organization_id, "
      + "         phone_work, "
      + "         email_work, "
      + "         notes "
      + "FROM     PERSON "
      + "WHERE    person_id = :personId "
      + "INTO     :firstName, "
      + "         :lastName, "
      + "         :pictureUrl, "
      + "         :dateOfBirth, "
      + "         :genderGroup, "
      + "         :phone, "
      + "         :mobile, "
      + "         :email, "
      + "         :street, "
      + "         :city, "
      + "         :country, "
      + "         :position, "
      + "         :organization, "
      + "         :phoneWork, "
      + "         :emailWork, "
      + "         :notes";

  String PERSON_UPDATE = ""
      + "UPDATE   PERSON "
      + "SET      first_name = :firstName, "
      + "         last_name = :lastName, "
      + "         picture_url = :pictureUrl, "
      + "         date_of_birth = :dateOfBirth, "
      + "         gender = :genderGroup, "
      + "         phone  = :phone, "
      + "         mobile = :mobile, "
      + "         email = :email, "
      + "         street = :street, "
      + "         city = :city, "
      + "         country = :country, "
      + "         position = :position, "
      + "         organization_id = :organization, "
      + "         phone_work = :phoneWork, "
      + "         email_work = :emailWork, "
      + "         notes = :notes "
      + "WHERE    person_id = :personId";

  String ORGANIZATION_INSERT_SAMPLE = ""
      + "INSERT   INTO ORGANIZATION "
      + "        (organization_id, "
      + "         name, "
      + "         city, "
      + "         country, "
      + "         url, "
      + "         logo_url) ";

  String ORGANIZATION_VALUES_01 = ""
      + "VALUES  ('org01', "
      + "         'Alice''s Adventures in Wonderland', "
      + "         'London', "
      + "         'GB', "
      + "         'http://en.wikipedia.org/wiki/Alice%27s_Adventures_in_Wonderland', "
      + "         'https://upload.wikimedia.org/wikipedia/en/3/3f/Alice_in_Wonderland%2C_cover_1865.jpg')";

  String ORGANIZATION_VALUES_02 = ""
      + "VALUES  ('org02', "
      + "         'BSI Business Systems Integration AG', "
      + "         'Daettwil, Baden', "
      + "         'CH', "
      + "         'https://www.bsi-software.com', "
      + "         'https://wiki.eclipse.org/images/4/4f/Bsiag.png')";

  String PERSON_INSERT_SAMPLE = ""
      + "INSERT   INTO PERSON "
      + "         (person_id, "
      + "          first_name, "
      + "          last_name, "
      + "          picture_url, "
      + "          date_of_birth, "
      + "          gender, "
      + "          street, "
      + "          city, "
      + "          country, "
      + "          position, "
      + "          organization_id) ";

  String PERSON_VALUES_01 = ""
      + "VALUES   ('prs01', "
      + "          'Alice', "
      + "          null, "
      + "          'http://www.uergsel.de/uploads/Alice.png', "
      + "          '26.11.1865', "
      + "          'F', "
      + "          null, "
      + "          'Daresbury, Cheshire', "
      + "          'GB', "
      + "          'The curious girl', "
      + "          'org01')";

  String PERSON_VALUES_02 = ""
      + "VALUES   ('prs02', "
      + "          'Rabbit', "
      + "          'White', "
      + "          'https://upload.wikimedia.org/wikipedia/commons/4/42/The_White_Rabbit_%28Tenniel%29_-_The_Nursery_Alice_%281890%29_-_BL.jpg', "
      + "          '26.11.1865', "
      + "          'M', "
      + "          null, "
      + "          'Daresbury, Cheshire', "
      + "          'GB', "
      + "          null, "
      + "          'org01')";

  String PERSON_VALUES_03 = ""
      + "VALUES   ('prs03', "
      + "          'Gegor', "
      + "          'Bauer', "
      + "          'https://wiki.eclipse.org/images/5/54/Scout_contacts_112.png', "
      + "          null, "
      + "          'M', "
      + "          null, "
      + "          'Aarau', "
      + "          'CH', "
      + "          null, "
      + "          'org02')";

  String PERSON_VALUES_04 = ""
      + "VALUES   ('prs04', "
      + "          'Alexandre', "
      + "          'Schroder', "
      + "          'https://wiki.eclipse.org/images/5/54/Scout_contacts_105.png', "
      + "          '30.05.1976', "
      + "          'M', "
      + "          'Zypressenstrasse 60', "
      + "          'Zürich', "
      + "          'CH', "
      + "          null, "
      + "          'org02')";

  String PERSON_DROP_TABLE = "DROP TABLE PERSON";
  String ORGANIZATION_DROP_TABLE = "DROP TABLE ORGANIZATION";

String DOCUMENT_CREATE_TABLE = ""
	      + "CREATE   TABLE DOCUMENT "
	      + "         (id VARCHAR(64) NOT NULL CONSTRAINT PERSON_PK PRIMARY KEY, "
	      + "          name VARCHAR(128), "
	      + "          data BLOB(16MB)"
	      + "         )";

String DOCUMENT_INSERT_SAMPLE = null;

String DOCUMENT_VALUES_01 = null;

String TEXT_CREATE_TABLE = ""
	      + "CREATE   TABLE TEXT "
	      + "         (locale VARCHAR(4) NOT NULL, "
	      + "          id VARCHAR(64) NOT NULL,"
	      + "          string VARCHAR(256),"
	      + "          PRIMARY KEY (locale, id)" 
	      + "         )";

String TEXT_INSERT_SAMPLE = ""
	      + "INSERT   INTO TEXT "
	      + "         (locale, "
	      + "          id, "
	      + "          string)";

String TEXT_VALUES_01 =  ""
	      + "VALUES   ('', "
	      + "          'root', "
	      + "          'Root')";

String ROLE_CREATE_TABLE = ""
	      + "CREATE   TABLE ROLE "
	      + "         (id VARCHAR(128) NOT NULL CONSTRAINT PERSON_PK PRIMARY KEY"
	      + "         )";

String ROLE_INSERT_SAMPLE = null;

String ROLE_VALUES_ROOT = null;

String ROLE_VALUES_USER = null;

String ROLE_PERMISSION_CREATE_TABLE = ""
	      + "CREATE   TABLE ROLE_PERMISSION "
	      + "         (role VARCHAR(128),"
	      + "          permission VARCHAR(256),"
	      + "          PRIMARY KEY (role, permission)"
	      + "         )";

String USER_CREATE_TABLE = null;

String USER_INSERT_SAMPLE = null;

String USER_VALUES_ROOT = null;

  // tag::organizationListing[]
  // tag::createDB[]
}
// end::createDB[]
// end::organizationListing[]
