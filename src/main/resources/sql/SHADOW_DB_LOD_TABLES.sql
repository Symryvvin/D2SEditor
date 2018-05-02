CREATE TABLE tbl_class (
  id           INT PRIMARY KEY NOT NULL,
  name         VARCHAR(11)     NOT NULL,
  gender       VARCHAR(1)      NOT NULL,
  is_expansion BOOLEAN         NOT NULL
);

CREATE TABLE tbl_skill (
  name        VARCHAR(25) NOT NULL,
  class_id    INT         NOT NULL,
  page        INT         NOT NULL,
  row_number  INT         NOT NULL,
  col_number  INT         NOT NULL,
  icon        CLOB        NOT NULL,
  byte_order  INT         NOT NULL,
  description CLOB,
  CONSTRAINT tbl_skill_tbl_class_id_fk FOREIGN KEY (class_id) REFERENCES tbl_class (id)
);

CREATE TABLE tbl_title (
  name         VARCHAR(10) NOT NULL,
  value        INT         NOT NULL,
  gender       VARCHAR(1)  NOT NULL,
  difficult    VARCHAR(10) NOT NULL,
  is_expansion BOOLEAN     NOT NULL,
  is_hardcore  BOOLEAN     NOT NULL
);

CREATE TABLE tbl_skill_page (
  name       VARCHAR(20) NOT NULL,
  class_id   INT         NOT NULL,
  page_order INT         NOT NULL,
  CONSTRAINT tbl_skill_page_tbl_class_id_fk FOREIGN KEY (class_id) REFERENCES tbl_class (id)
);

CREATE TABLE tbl_attribute (
  id     INT PRIMARY KEY NOT NULL,
  name   VARCHAR(20)     NOT NULL,
  length INT             NOT NULL
);

CREATE TABLE tbl_waypoint (
  position INT         NOT NULL,
  name     VARCHAR(20) NOT NULL,
  act      INT         NOT NULL
);