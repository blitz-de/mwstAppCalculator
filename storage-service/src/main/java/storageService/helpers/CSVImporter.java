package storageService.helpers;

/**
 * Import CSV-Data which are primarily exported from the application service
 */
public class CSVImporter {

    //import all data from the db

    // convert db-data -> csv_Form

    // import Data from here to file csv.


//    public void sqlToCSV (String query, String filename){
//
//        log.info("creating csv file: " + filename);
//
//        try {
//
//            FileWriter fw = new FileWriter(filename + ".csv");
//            if(conn.isClosed()) st = conn.createStatement();
//            ResultSet rs = st.executeQuery(query);
//
//            int cols = rs.getMetaData().getColumnCount();
//
//            for(int i = 1; i <= cols; i ++){
//                fw.append(rs.getMetaData().getColumnLabel(i));
//                if(i < cols) fw.append(',');
//                else fw.append('\n');
//            }
//
//            while (rs.next()) {
//
//                for(int i = 1; i <= cols; i ++){
//                    fw.append(rs.getString(i));
//                    if(i < cols) fw.append(',');
//                }
//                fw.append('\n');
//            }
//            fw.flush();
//            fw.close();
//            log.info("CSV File is created successfully.");
//            conn.close();
//        } catch (Exception e) {
//            log.fatal(e);
//        }
//    }
}
