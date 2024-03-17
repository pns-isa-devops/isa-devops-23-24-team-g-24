export class PartnerNotFound extends Error {
    constructor(namePartner: string) {
        super(`We did not find a partner with the name "${namePartner}"`);
    }
}
