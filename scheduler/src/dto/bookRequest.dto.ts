import { IsNotEmpty,IsString } from 'class-validator';

export class BookRequestDto {
    @IsNotEmpty()
    @IsString()
    dateBook: string;

    @IsNotEmpty()
    @IsString()
    nameActivity: string;

    @IsNotEmpty()
    @IsString()
    namePartner: string;
}
