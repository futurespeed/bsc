package org.fs.bsc.flow.editor.command;

import java.util.Stack;

public class CommandManager {
    private Stack<Command> commands;

    private Command currentCommand;

    public CommandManager(){
        commands = new Stack<>();
    }

    public synchronized void execute(Command command){
        if(commands.size() > 100){
            commands.removeElementAt(0);
        }
        command.execute();
        commands.push(command);
        setCurrentCommand(null);
    }

    public synchronized void undo(){
        Command command = commands.pop();
        command.undo();
    }

    public Stack<Command> getCommands() {
        return commands;
    }

    public void setCommands(Stack<Command> commands) {
        this.commands = commands;
    }

    public Command getCurrentCommand() {
        return currentCommand;
    }

    public void setCurrentCommand(Command currentCommand) {
        this.currentCommand = currentCommand;
    }
}
