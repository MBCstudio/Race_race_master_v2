package org.example;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Klasa odpowiadajaca za funkcjonowanie planszy i zajmujaca sie mechanika znajdujacych sie na niej obiektow
 */
public class Board {
    int width;
    int height;
    Map<Position, Tribe> board = new HashMap<>();

    /**
     * konstruktor odpowiadajacy za plansze
     * @param width
     * @param height
     */
    public Board(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * metoda przypisujaca wylosowana pozycje poczatkowa dla obiektu plemienia i dodajaca ja do mapy za pomoca metody addToBoard
     * @param tribe
     */
    public void addTribe(Tribe tribe) {
        Position position = this.drawPosition();
        tribe.current_x = position.x;
        tribe.current_y = position.y;
        this.addToBoard(tribe, position);
    }

    /**
     * metoda ograniczajaca losowane pozycje adekwatnie do rozmiaru mapy i zwracajaca losowa pozycje w tym zakresie
     * @return Position
     */
    private Position drawPosition() {
        Position position = Position.drawPosition(width - 1, height - 1);
        while (this.board.containsKey(position)) {
            position = Position.drawPosition(width - 1, height - 1);
        }
        return position;
    }

    /**
     * metoda dodaje obiekt plemienia do mapy dodatkowo sprawdzajaca czy jakis inny obiekt nie stoi na danym miejscu
     * @param tribe
     * @param position
     */
    private void addToBoard(Tribe tribe, Position position){
        if (!this.board.containsKey(position)){
            this.board.put(position, tribe);
        }
    }

    /**
     * metoda odpowiada za przesuwanie plemion w okreslonym kierunku
     * @param tribe
     * @param position
     */
    public void moveTribe(Tribe tribe, Position position) {
        int random = RandInt.random(0, 2);
        if (RandInt.random(-100, 100) < 0){
            position.x -= random * tribe.multiply_speed_x;
            if (position.x  < 0){ //zabeczpiecznie przed wyjsciem poza szerokosc planszy
                position.x = 0;
            }
        } else {
            position.x += random * tribe.multiply_speed_x; //losuje o ile przesłunie sie obiekt (lewo/prawo)
            if (position.x >= width){
                position.x = width-1;
            }
        }
        if (RandInt.random(-100, 100) < 0){
            position.y -= random * tribe.multiply_speed_y; //losuje o ile przesłunie sie obiekt (gora/dol)
            if (position.y < 0) {
                position.y = 0;
            }
        } else {
            position.y += random * tribe.multiply_speed_y; //losuje o ile przesłunie sie obiekt (lewo/prawo)
            if (position.y >= height) {
                position.y = height - 1;
            }
        }
        tribe.current_x = position.x;
        tribe.current_y = position.y;
    }

    /**
     * metoda odpowiadająca za mechanikę walki pomiędzy plemionami
     * @param listOfFighters
     * @return
     */
    public Tribe tribeFight(List<Tribe> listOfFighters){
        int choice = RandInt.random(1,4);
        int max = 0;
        Tribe winner = new Tribe();
        for (Tribe fighter : listOfFighters) {
            if (choice ==1){
                if (fighter.physical_strength > max){
                    max = fighter.physical_strength;
                    winner = fighter;
                }
            } else if (choice == 2) {
                if (fighter.iq > max) {
                    max = fighter.iq;
                    winner = fighter;
                }
            }else if (choice == 3){
                if (fighter.agility > max){
                    max = fighter.agility;
                    winner = fighter;
                }
            } else if (choice == 4) {
                if (fighter.endurance > max) {
                    max = fighter.endurance;
                    winner = fighter;
                }
            }
        }
        return winner;
    }

    /**
     * Metoda, która odpowiada za wyświtlanie tablicy, na ktorej rozmieszczeni sa czlonkowie plemion
     */
    public void displayBoard() {
        for (int i=0; i<height; i++){
            for (int j=0; j<width; j++) {
                Tribe tribe = board.get(new Position(j, i));
                if (tribe == null) {
                    System.out.print(".");
                } else {
                    System.out.print(tribe.getName().substring(0,1));
                }
            }
            System.out.println();
        }
    }

    /**
     * Metoda odpowiadająca za ponowne umieszczenie obiektow do mapy po poruszaniu i walkach
     * @param tribe
     * @param x
     * @param y
     */
    public void newTribe (Tribe tribe, int x, int y) {
        tribe.current_x = x;
        tribe.current_y = y;
        this.board.put(new Position(x, y), tribe);
    }
}
