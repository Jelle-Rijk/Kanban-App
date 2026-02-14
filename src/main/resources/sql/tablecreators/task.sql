CREATE TABLE IF NOT EXISTS "Task" (
	"TaskId"	INTEGER,
	"Description"	TEXT NOT NULL,
	"Stage"	INTEGER NOT NULL,
	"BoardId"	INTEGER NOT NULL,
	"Completed"	INTEGER NOT NULL CHECK(Completed IN (0, 1)),
	PRIMARY KEY("TaskId" AUTOINCREMENT),
	FOREIGN KEY("Stage", "BoardId") REFERENCES "Stage"("Number", "BoardId")
)