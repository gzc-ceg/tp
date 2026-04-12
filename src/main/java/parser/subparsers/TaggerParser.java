package parser.subparsers;

import exception.JobPilotException;
import parser.ParsedCommand;
import task.IndustryTag;

/**
 * Parses the tag command.
 * Format: tag INDEX add/TAG or tag INDEX remove/TAG
 */
public class TaggerParser {

    public static ParsedCommand parse(String input) throws JobPilotException {
        int addIndex = input.indexOf(" add/");
        int removeIndex = input.indexOf(" remove/");
        boolean isAdd = addIndex != -1;

        if (!isAdd && removeIndex == -1) {
            throw new JobPilotException("Invalid format! Use: tag INDEX add/TAG or tag INDEX remove/TAG");
        }

        int commandIndex = isAdd ? addIndex : removeIndex;
        String indexStr = input.substring("tag".length(), commandIndex).trim();
        int listIndex;
        try {
            listIndex = Integer.parseInt(indexStr) - 1;
        } catch (NumberFormatException e) {
            throw new JobPilotException("Invalid index! Use a number.");
        }

        // " add/" is 5 chars; " remove/" is 8 chars (leading space + "remove/")
        int tagStartIndex = commandIndex + (isAdd ? 5 : 8);
        String tagStr = input.substring(tagStartIndex).trim();
        if (tagStr.isEmpty()) {
            throw new JobPilotException("Tag cannot be empty!");
        }
        IndustryTag tag = new IndustryTag(tagStr);

        return new ParsedCommand(listIndex, tag, isAdd);
    }
}