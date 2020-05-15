package com.sber;

@Deprecated
public class CoronaDesinfector {

    @InjectByType
    private Announcer announcer;

    @InjectByType
    private Policeman policeman;

    public void start(Room room){
        announcer.announce("начинаем дезинфекцию, все вон!");
        policeman.makePeopleLeaveRoom();
        //todo сообщить все присутствующим в комнате, о начале дезинфекции и пропросить разогнать всех
        desinfect(room);
        announcer.announce("можете рискнуть зайти обратно ");

        //todo сообщить всем присутствубщим в комнате, что они могут вернуться обратно
    }

    private void desinfect(Room room){
        System.out.println("коронавирус изыди");
    }
}
