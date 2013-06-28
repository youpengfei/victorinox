package org.youyou.utils;

import au.com.bytecode.opencsv.CSVReader;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * @author youpengfeiinfo
 * @version 13-6-14
 */
public class MoveUtils {
    public static void moveData(String fileName, String tableFullName, String deleteValue,String columns) throws SQLException, IOException {
        System.out.println("========begin========");
        Reader reader = IOUtils.getInputStreamReader(MoveUtils.class, fileName);
        CSVReader cr = new CSVReader(reader);
        final List<String[]> fileData = cr.readAll();
        String header[] = fileData.get(0);
        String params[] = new String[header.length];
        for (int i = 0; i < params.length; i++) {
            params[i] = "?";
        }
        int removeStatusIndex = ArrayUtils.indexOf(header, "yn");
        final Connection connection = JdbcUtil.getConnection();
        final String sql =
                new StringBuffer("insert INTO ")
                        .append(tableFullName).append("(")
                        .append(columns)
                        .append(")")
                        .append("VALUES(")
                        .append(StringUtils.join(params, ",").trim())
                        .append(")").toString();
        connection.setAutoCommit(false);
        connection.createStatement().execute("truncate table " + tableFullName);
        connection.createStatement().execute("SET IDENTITY_INSERT "+tableFullName+"  ON ");
        final PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i = 1; i < fileData.size(); i++) {
            String[] strings = fileData.get(i);
            if (deleteValue.equals(strings[removeStatusIndex])) {
                for (int j = 1; j <= strings.length; j++) {
                    String parameter = strings[j - 1];
                    if ("NULL".equals(parameter)) {
                        parameter = null;
                    }
                    preparedStatement.setObject(j, parameter);
                }
                System.out.println(sql);
                preparedStatement.addBatch();
            }
        }
        final int[] ints = preparedStatement.executeBatch();
        connection.commit();
        JdbcUtil.close(null, preparedStatement, connection);
        System.out.println("=======end=======");
    }
}
