echo CLEAR DATABASE
sqlite3 shadow.db ".read sql/SHADOW_DB_DROP_TABLES.sql"
echo CREATE TABLES
sqlite3 shadow.db ".read sql/SHADOW_DB_LOD_TABLES.sql"
sqlite3 shadow.db ".read sql/SHADOW_DB_LOD_TBL_CLASS.sql"
sqlite3 shadow.db ".read sql/SHADOW_DB_LOD_TBL_ATTRIBUTE.sql"
sqlite3 shadow.db ".read sql/SHADOW_DB_LOD_TBL_TITLE.sql"
sqlite3 shadow.db ".read sql/SHADOW_DB_LOD_TBL_SKILL.sql"
sqlite3 shadow.db ".read sql/SHADOW_DB_LOD_TBL_SKILL_PAGE.sql"
sqlite3 shadow.db ".read sql/SHADOW_DB_LOD_TBL_WAYPOINT.sql"
sqlite3 shadow.db ".read sql/SHADOW_DB_LOD_TBL_LEVEL.sql"