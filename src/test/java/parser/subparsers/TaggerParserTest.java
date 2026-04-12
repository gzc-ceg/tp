package parser.subparsers;

import exception.JobPilotException;
import org.junit.jupiter.api.Test;
import parser.ParsedCommand;

import static org.junit.jupiter.api.Assertions.*;

class TaggerParserTest {

    @Test
    void parse_remove_extractsTagWithoutLeadingSlash() throws JobPilotException {
        ParsedCommand cmd = TaggerParser.parse("tag 1 remove/FINANCE");
        assertFalse(cmd.isAddTag());
        assertEquals("FINANCE", cmd.getTag().getTagName());
    }

    @Test
    void parse_add_emptyTag_throws() {
        assertThrows(JobPilotException.class, () -> TaggerParser.parse("tag 1 add/"));
    }

    @Test
    void parse_remove_emptyTag_throws() {
        assertThrows(JobPilotException.class, () -> TaggerParser.parse("tag 1 remove/"));
    }
}
