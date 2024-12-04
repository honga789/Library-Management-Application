package dha.libapp.syncdao;

import dha.libapp.dao.GenreTypeDAO;
import dha.libapp.models.GenreType;
import dha.libapp.syncdao.utils.DAOExecuteCallback;
import dha.libapp.syncdao.utils.DAOTaskRunner;
import dha.libapp.syncdao.utils.DAOUpdateCallback;
import javafx.concurrent.Task;

import java.util.List;

public class GenreTypeSyncDAO {
    public static void getAllGenreTypeSync(DAOExecuteCallback<List<GenreType>> callback) {
        Task<List<GenreType>> task = new Task<List<GenreType>>() {

            @Override
            protected List<GenreType> call() throws Exception {
                return GenreTypeDAO.getAllGenreType();
            }
        };
        DAOTaskRunner.executeTask(task, callback);
    }

    public static void getGenreTypeByIdSync(int genre_id, DAOExecuteCallback<GenreType> callback) {
        Task<GenreType> task = new Task<>() {

            @Override
            protected GenreType call() throws Exception {
                return GenreTypeDAO.getGenreTypeById(genre_id);
            }
        };
        DAOTaskRunner.executeTask(task, callback);
    }

    public static void getGenreTypeByNameSync(String genre_name, DAOExecuteCallback<GenreType> callback) {
        Task<GenreType> task = new Task<GenreType>() {

            @Override
            protected GenreType call() throws Exception {
                return GenreTypeDAO.getGenreTypeByName(genre_name);
            }
        };
        DAOTaskRunner.executeTask(task, callback);
    }
}
