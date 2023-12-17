package diagnostics.appointment;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TimeInterval {
	private int id;
	private int duration;
	private String day;
	private String start_time;
	
	public TimeInterval(int id_val, int dur_val, String day_val, String st_val) {
		id = id_val;
		duration = dur_val;
		day = day_val;
		start_time = st_val;
	}
	public TimeInterval(int dur_val, String day_val, String st_val) {
		id = -1;
		duration = dur_val;
		day = day_val;
		start_time = st_val;
	}
	public TimeInterval(ResultSet rt) throws SQLException {
		try{ id = rt.getInt("interval_id"); } catch(SQLException e) {}
		try{ duration = rt.getInt("duration"); } catch(SQLException e) {}
		try{ day = rt.getString("day"); } catch(SQLException e) {}
		try{ start_time = rt.getString("start_time"); } catch(SQLException e) {}
	}
	
	public final int getId() {
		return id;
	}
	
	public static final String getinitFields() {
		return "TimeInterval.interval_id, TimeInterval.duration, TimeInterval.day, TimeInterval.start_time";
	}
	public static final String getAppSelectFields() {
		return "TimeInterval.start_time";
	}
	public final String getStartTime() {
		return start_time;
	}
}
