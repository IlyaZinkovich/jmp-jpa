CREATE TABLE "UNIT" (
    "ID" INT(11) NOT NULL AUTO_INCREMENT,
    "NAME" VARCHAR(60) NOT NULL,
    PRIMARY KEY ("ID")
);

CREATE TABLE "PERSONAL_INFO" (
    "ID" INT(11) NOT NULL AUTO_INCREMENT,
    "NAME" VARCHAR(60) NOT NULL,
    PRIMARY KEY ("ID")
);

CREATE TABLE "EMPLOYEE" (
    "ID" INT(11) NOT NULL AUTO_INCREMENT,
    "CITY" VARCHAR(60) NOT NULL,
    "STREET" VARCHAR(60) DEFAULT NULL,
    "HOUSE" VARCHAR(60) DEFAULT NULL,
    "APARTMENT" VARCHAR(60) DEFAULT NULL,
    "STATUS" VARCHAR(60) DEFAULT NULL,
    "UNIT_ID" INT(11) NOT NULL,
    "PERSONAL_INFO_ID" INT(11) NOT NULL,
    CONSTRAINT "FK_UNIT_ID" FOREIGN KEY ("UNIT_ID") REFERENCES "UNIT" ("ID"),
    CONSTRAINT "FK_PI_EMPLOYEE_ID" FOREIGN KEY ("PERSONAL_INFO_ID") REFERENCES "PERSONAL_INFO" ("ID"),
    PRIMARY KEY ("ID")
);

CREATE TABLE "PROJECT" (
    "ID" INT(11) NOT NULL AUTO_INCREMENT,
    "NAME" VARCHAR(60) DEFAULT NULL,
    PRIMARY KEY ("ID")
);

CREATE TABLE "EMPLOYEE_PROJECTS" (
    "EMPLOYEE_ID" INT(11) NOT NULL,
    "PROJECT_ID" INT(11) NOT NULL,
    CONSTRAINT "FK_EMPLOYEE_ID" FOREIGN KEY ("EMPLOYEE_ID") REFERENCES "EMPLOYEE" ("ID"),
    CONSTRAINT "FK_PROJECT_ID" FOREIGN KEY ("PROJECT_ID") REFERENCES "EMPLOYEE" ("ID")
);