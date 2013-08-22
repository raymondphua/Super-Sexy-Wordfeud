package wordfeud.core.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/*
 * 
 * Take from http://stackoverflow.com/a/4265371
 */
public class Queries {
	public static final String CURRENT_TILES;
	public static final String RANKING;
	public static final String TILE;
	public static final String LATEST_TURN;
	public static final String TURN_TILES;
	public static final String TURN_TILES_OB;

	static {
		CURRENT_TILES = Queries.readFile("queries/currentTiles.sql");
		RANKING = Queries.readFile("queries/ranking.sql");
		TILE = Queries.readFile("queries/tileQuery.sql");
		LATEST_TURN = Queries.readFile("queries/latestTurnForPlayer.sql");
		TURN_TILES = Queries.readFile("queries/tilesForTurns.sql");
		TURN_TILES_OB = Queries.readFile("queries/tilesForTurnsOb.sql");
	}

	private static String readFile(final String file) {
		BufferedReader reader = null;
		StringBuilder stringBuilder = new StringBuilder();
		try {
			reader = new BufferedReader(new FileReader(file));

			String line = null;
			String ls = System.getProperty("line.separator");

			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);
				stringBuilder.append(ls);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stringBuilder.toString();
	}
}
