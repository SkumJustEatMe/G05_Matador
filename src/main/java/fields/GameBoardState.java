package fields;

public class GameBoardState {

    private final Field[] fieldsArray;

    private FieldState[] fieldStates;
    public FieldState getFieldState(int indexOfGameBoard) {return this.fieldStates[indexOfGameBoard];}

    public GameBoardState(Field[] fieldsArray)
    {
        this.fieldsArray = fieldsArray;
        this.fieldStates = populateFieldStatesFromGameBoard();
    }

    private FieldState[] populateFieldStatesFromGameBoard() {
        fieldStates = new FieldState[this.fieldsArray.length];

        for(int i = 0; i < fieldsArray.length; i++)
        {
            switch (GameBoard.fieldsArray[i].getType())
            {
                case STREET, BREWERY, FERRY -> fieldStates[i] = new FieldState();
                default -> fieldStates[i] = null;
            }
        }
        return fieldStates;
    }
}
