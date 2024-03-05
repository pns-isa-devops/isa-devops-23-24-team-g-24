export class ActivityNotFound extends Error {
    constructor(nameActivity: string) {
        super(`We did not find an activity with the name "${nameActivity}"`);
    }
}
