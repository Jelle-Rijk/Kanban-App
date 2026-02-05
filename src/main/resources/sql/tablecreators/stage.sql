CREATE TABLE IF NOT EXISTS "Stage" (
	"Number" INTEGER,
	"BoardId" INTEGER,
	"Title" TEXT NOT NULL,
	"Limit" INTEGER,
	PRIMARY KEY("Number","BoardId"),
	FOREIGN KEY("BoardId") REFERENCES "Board"("BoardId")
);