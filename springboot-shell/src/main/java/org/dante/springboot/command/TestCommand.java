package org.dante.springboot.command;

import org.crsh.cli.Command;
import org.crsh.cli.Usage;
import org.crsh.command.BaseCommand;

@Usage("Test Command")
public class TestCommand extends BaseCommand{

    @Command
    @Usage("Useage main Command")
    public String main (){
        return "main command";
    }

}
