CREATE TABLE IF NOT EXISTS "Task" (
	"TaskId"	INTEGER,
	"Description"	TEXT NOT NULL,
	"Stage"	INTEGER NOT NULL,
	"BoardId"	INTEGER NOT NULL,
	"Completed"	INTEGER NOT NULL CHECK(Completed IN (0, 1)),
	FOREIGN KEY("Stage") REFERENCES "Stage"("StageNumber"),
	PRIMARY KEY("TaskId" AUTOINCREMENT),
	FOREIGN KEY("BoardId") REFERENCES "Board"("BoardId")
)