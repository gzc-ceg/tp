package task;

import exception.JobPilotException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

/**
 * Handles editing existing job applications.
 */
public class Edit {

    /**
     * Edits an existing application based on user input.
     * Format: edit INDEX [c/COMPANY] [p/POSITION] [d/DATE] [s/STATUS]
     *
     * @param input The full user command (e.g., "edit 1 c/Google")
     * @param applications The list storing all job applications
     * @throws JobPilotException If parsing fails or index is invalid
     */
    public static void editApplication(String input, ArrayList<Add> applications)
            throws JobPilotException {
        try {
            // Normalize whitespace (replace multiple spaces with single space)
            String normalized = input.trim().replaceAll("\\s+", " ");
            String[] parts = normalized.split(" ");

            if (parts.length < 2) {
                throw new JobPilotException(
                        "Please provide an index. Example: edit 1 c/Google");
            }

            int index = Integer.parseInt(parts[1]) - 1;
            if (index < 0 || index >= applications.size()) {
                throw new JobPilotException(
                        "Invalid application number! You have " +
                                applications.size() + " application(s).");
            }

            Add target = applications.get(index);
            boolean updated = false;

            // Find the start of fields (skip "edit INDEX")
            int fieldsStart = normalized.indexOf(" ", normalized.indexOf(" ") + 1) + 1;
            if (fieldsStart <= 0 || fieldsStart >= normalized.length()) {
                throw new JobPilotException(
                        "No valid fields to update! Use: c/COMPANY, p/POSITION, " +
                                "d/DATE, or s/STATUS");
            }

            String remaining = normalized.substring(fieldsStart);

            // Parse each field by finding prefixes
            int pos = 0;
            while (pos < remaining.length()) {
                if (remaining.startsWith("c/", pos)) {
                    int nextPos = findNextPrefix(remaining, pos + 2);
                    String value = remaining.substring(pos + 2, nextPos).trim();
                    if (value.isEmpty()) {
                        throw new JobPilotException("Company name cannot be empty!");
                    }
                    target.setCompany(value);
                    updated = true;
                    pos = nextPos;
                } else if (remaining.startsWith("p/", pos)) {
                    int nextPos = findNextPrefix(remaining, pos + 2);
                    String value = remaining.substring(pos + 2, nextPos).trim();
                    if (value.isEmpty()) {
                        throw new JobPilotException("Position cannot be empty!");
                    }
                    target.setPosition(value);
                    updated = true;
                    pos = nextPos;
                } else if (remaining.startsWith("d/", pos)) {
                    int nextPos = findNextPrefix(remaining, pos + 2);
                    String value = remaining.substring(pos + 2, nextPos).trim();
                    if (value.isEmpty()) {
                        throw new JobPilotException("Date cannot be empty!");
                    }
                    validateDate(value);
                    target.setDate(value);
                    updated = true;
                    pos = nextPos;
                } else if (remaining.startsWith("s/", pos)) {
                    int nextPos = findNextPrefix(remaining, pos + 2);
                    String value = remaining.substring(pos + 2, nextPos).trim();
                    if (value.isEmpty()) {
                        throw new JobPilotException("Status cannot be empty!");
                    }
                    target.setStatus(value);
                    updated = true;
                    pos = nextPos;
                } else {
                    pos++;
                }
            }

            if (!updated) {
                throw new JobPilotException(
                        "No valid fields to update! Use: c/COMPANY, p/POSITION, " +
                                "d/DATE, or s/STATUS");
            }

            System.out.println("Updated application:");
            System.out.println(target);

        } catch (NumberFormatException e) {
            throw new JobPilotException("Invalid index! Use a number: edit 1 c/Google");
        } catch (DateTimeParseException e) {
            throw new JobPilotException(
                    "Invalid date! Use YYYY-MM-DD format (e.g., 2024-09-12)");
        }
    }

    /**
     * Finds the next prefix (c/, p/, d/, s/) starting from the given position.
     *
     * @param str The string to search
     * @param start The starting position
     * @return The index of the next prefix, or the end of string if none found
     */
    private static int findNextPrefix(String str, int start) {
        int cIndex = str.indexOf("c/", start);
        int pIndex = str.indexOf("p/", start);
        int dIndex = str.indexOf("d/", start);
        int sIndex = str.indexOf("s/", start);

        int next = str.length();
        if (cIndex != -1 && cIndex < next) next = cIndex;
        if (pIndex != -1 && pIndex < next) next = pIndex;
        if (dIndex != -1 && dIndex < next) next = dIndex;
        if (sIndex != -1 && sIndex < next) next = sIndex;

        return next;
    }

    /**
     * Validates that the date string is in YYYY-MM-DD format.
     *
     * @param dateStr The date string to validate
     * @throws DateTimeParseException If format is invalid
     */
    private static void validateDate(String dateStr) throws DateTimeParseException {
        LocalDate.parse(dateStr);
    }
}