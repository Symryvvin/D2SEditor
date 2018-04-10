CREATE TABLE tbl_class (
  id        INTEGER     NOT NULL,
  name      VARCHAR(11) NOT NULL,
  gender    VARCHAR(1)  NOT NULL,
  expansion BOOLEAN
);
CREATE UNIQUE INDEX tbl_class_id_uindex
  ON tbl_class (id);
CREATE UNIQUE INDEX tbl_class_name_uindex
  ON tbl_class (name);
COMMENT ON TABLE tbl_class IS 'Present byte value and names of character classes';

CREATE TABLE tbl_skill (
  name       VARCHAR(25) NOT NULL,
  class_id   INTEGER     NOT NULL,
  page       INTEGER     NOT NULL,
  skill_row  INTEGER     NOT NULL,
  skill_col  INTEGER     NOT NULL,
  icon       CLOB        NOT NULL,
  byte_order INTEGER     NOT NULL,
  CONSTRAINT tbl_skill_tbl_class_id_fk FOREIGN KEY (class_id) REFERENCES tbl_class (id)
);
COMMENT ON TABLE tbl_skill IS 'Skills data';

CREATE TABLE tbl_title (
  name         VARCHAR(10) NOT NULL,
  byte_value   TINYINT     NOT NULL,
  gender       VARCHAR(1),
  difficult    VARCHAR(10),
  is_expansion BOOLEAN,
  is_hardcore  BOOLEAN
);
COMMENT ON TABLE tbl_title IS 'Character titles data';

