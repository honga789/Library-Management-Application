package dha.libapp.syncdao;

import dha.libapp.dao.GenreTypeDAO;
import dha.libapp.models.GenreType;
import dha.libapp.syncdao.utils.DAOExecuteCallback;
import dha.libapp.syncdao.utils.DAOTaskRunner;
import javafx.concurrent.Task;

public class GenreTypeSyncDAO {
    public static void getGenreTypeByNameSync (String genre_name, DAOExecuteCallback<GenreType> callback) {
        Task<GenreType> task = new Task<GenreType>() {

            @Override
            protected GenreType call() throws Exception {
                return GenreTypeDAO.getGenreTypeByName(genre_name);
            }
        };
        DAOTaskRunner.executeTask(task, callback);
    }
}
