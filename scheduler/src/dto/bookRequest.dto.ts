import { IsNotEmpty,IsString } from 'class-validator';

export class BookRequestDto {
    @IsNotEmpty()
    @IsString()
    nameActivity: string;

    @IsNotEmpty()
    @IsString()
    namePartner: string;
}
