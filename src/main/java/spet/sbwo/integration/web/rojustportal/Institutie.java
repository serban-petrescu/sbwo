package spet.sbwo.integration.web.rojustportal;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Institutie.
 * <p>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Institutie"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="CurteadeApelBUCURESTI"/&gt;
 *     &lt;enumeration value="TribunalulBUCURESTI"/&gt;
 *     &lt;enumeration value="JudecatoriaSECTORUL4BUCURESTI"/&gt;
 *     &lt;enumeration value="TribunalulTIMIS"/&gt;
 *     &lt;enumeration value="CurteadeApelBACAU"/&gt;
 *     &lt;enumeration value="CurteadeApelCLUJ"/&gt;
 *     &lt;enumeration value="CurteadeApelORADEA"/&gt;
 *     &lt;enumeration value="CurteadeApelCONSTANTA"/&gt;
 *     &lt;enumeration value="CurteadeApelSUCEAVA"/&gt;
 *     &lt;enumeration value="TribunalulBOTOSANI"/&gt;
 *     &lt;enumeration value="CurteadeApelPLOIESTI"/&gt;
 *     &lt;enumeration value="CurteadeApelTARGUMURES"/&gt;
 *     &lt;enumeration value="CurteadeApelGALATI"/&gt;
 *     &lt;enumeration value="CurteadeApelIASI"/&gt;
 *     &lt;enumeration value="CurteadeApelPITESTI"/&gt;
 *     &lt;enumeration value="CurteadeApelCRAIOVA"/&gt;
 *     &lt;enumeration value="JudecatoriaARAD"/&gt;
 *     &lt;enumeration value="CurteadeApelALBAIULIA"/&gt;
 *     &lt;enumeration value="CurteadeApelTIMISOARA"/&gt;
 *     &lt;enumeration value="TribunalulBRASOV"/&gt;
 *     &lt;enumeration value="TribunalulDOLJ"/&gt;
 *     &lt;enumeration value="CurteadeApelBRASOV"/&gt;
 *     &lt;enumeration value="CurteaMilitaradeApelBUCURESTI"/&gt;
 *     &lt;enumeration value="TribunalulSATUMARE"/&gt;
 *     &lt;enumeration value="TribunalulSALAJ"/&gt;
 *     &lt;enumeration value="TribunalulSIBIU"/&gt;
 *     &lt;enumeration value="TribunalulSUCEAVA"/&gt;
 *     &lt;enumeration value="TribunalulTELEORMAN"/&gt;
 *     &lt;enumeration value="TribunalulTULCEA"/&gt;
 *     &lt;enumeration value="TribunalulVASLUI"/&gt;
 *     &lt;enumeration value="TribunalulVALCEA"/&gt;
 *     &lt;enumeration value="TribunalulVRANCEA"/&gt;
 *     &lt;enumeration value="TribunalulMilitarBUCURESTI"/&gt;
 *     &lt;enumeration value="TribunalulILFOV"/&gt;
 *     &lt;enumeration value="JudecatoriaBUFTEA"/&gt;
 *     &lt;enumeration value="TribunalulGORJ"/&gt;
 *     &lt;enumeration value="TribunalulHARGHITA"/&gt;
 *     &lt;enumeration value="TribunalulHUNEDOARA"/&gt;
 *     &lt;enumeration value="TribunalulIALOMITA"/&gt;
 *     &lt;enumeration value="TribunalulIASI"/&gt;
 *     &lt;enumeration value="TribunalulMARAMURES"/&gt;
 *     &lt;enumeration value="TribunalulMEHEDINTI"/&gt;
 *     &lt;enumeration value="TribunalulMURES"/&gt;
 *     &lt;enumeration value="TribunalulNEAMT"/&gt;
 *     &lt;enumeration value="TribunalulOLT"/&gt;
 *     &lt;enumeration value="TribunalulPRAHOVA"/&gt;
 *     &lt;enumeration value="TribunalulALBA"/&gt;
 *     &lt;enumeration value="TribunalulARAD"/&gt;
 *     &lt;enumeration value="TribunalulARGES"/&gt;
 *     &lt;enumeration value="TribunalulBACAU"/&gt;
 *     &lt;enumeration value="TribunalulBIHOR"/&gt;
 *     &lt;enumeration value="TribunalulBISTRITANASAUD"/&gt;
 *     &lt;enumeration value="TribunalulBRAILA"/&gt;
 *     &lt;enumeration value="TribunalulBUZAU"/&gt;
 *     &lt;enumeration value="TribunalulCARASSEVERIN"/&gt;
 *     &lt;enumeration value="TribunalulCALARASI"/&gt;
 *     &lt;enumeration value="TribunalulCLUJ"/&gt;
 *     &lt;enumeration value="TribunalulCONSTANTA"/&gt;
 *     &lt;enumeration value="TribunalulCOVASNA"/&gt;
 *     &lt;enumeration value="TribunalulDAMBOVITA"/&gt;
 *     &lt;enumeration value="TribunalulGALATI"/&gt;
 *     &lt;enumeration value="TribunalulGIURGIU"/&gt;
 *     &lt;enumeration value="JudecatoriaADJUD"/&gt;
 *     &lt;enumeration value="JudecatoriaAGNITA"/&gt;
 *     &lt;enumeration value="JudecatoriaAIUD"/&gt;
 *     &lt;enumeration value="JudecatoriaALBAIULIA"/&gt;
 *     &lt;enumeration value="JudecatoriaALESD"/&gt;
 *     &lt;enumeration value="JudecatoriaBABADAG"/&gt;
 *     &lt;enumeration value="JudecatoriaBACAU"/&gt;
 *     &lt;enumeration value="JudecatoriaBAIADEARAMA"/&gt;
 *     &lt;enumeration value="JudecatoriaBAIAMARE"/&gt;
 *     &lt;enumeration value="JudecatoriaBAILESTI"/&gt;
 *     &lt;enumeration value="JudecatoriaBALS"/&gt;
 *     &lt;enumeration value="JudecatoriaBALCESTI"/&gt;
 *     &lt;enumeration value="JudecatoriaBECLEAN"/&gt;
 *     &lt;enumeration value="JudecatoriaBEIUS"/&gt;
 *     &lt;enumeration value="JudecatoriaBICAZ"/&gt;
 *     &lt;enumeration value="JudecatoriaBARLAD"/&gt;
 *     &lt;enumeration value="JudecatoriaBISTRITA"/&gt;
 *     &lt;enumeration value="JudecatoriaBLAJ"/&gt;
 *     &lt;enumeration value="JudecatoriaBOLINTINVALE"/&gt;
 *     &lt;enumeration value="JudecatoriaBOTOSANI"/&gt;
 *     &lt;enumeration value="JudecatoriaBOZOVICI"/&gt;
 *     &lt;enumeration value="JudecatoriaBRAD"/&gt;
 *     &lt;enumeration value="JudecatoriaBRAILA"/&gt;
 *     &lt;enumeration value="JudecatoriaBRASOV"/&gt;
 *     &lt;enumeration value="JudecatoriaBREZOI"/&gt;
 *     &lt;enumeration value="JudecatoriaBUHUSI"/&gt;
 *     &lt;enumeration value="JudecatoriaBUZAU"/&gt;
 *     &lt;enumeration value="JudecatoriaCALAFAT"/&gt;
 *     &lt;enumeration value="JudecatoriaCALARASI"/&gt;
 *     &lt;enumeration value="JudecatoriaCAMPENI"/&gt;
 *     &lt;enumeration value="JudecatoriaCAMPINA"/&gt;
 *     &lt;enumeration value="JudecatoriaCAMPULUNG"/&gt;
 *     &lt;enumeration value="JudecatoriaCAMPULUNGMOLDOVENESC"/&gt;
 *     &lt;enumeration value="JudecatoriaCARACAL"/&gt;
 *     &lt;enumeration value="JudecatoriaCARANSEBES"/&gt;
 *     &lt;enumeration value="JudecatoriaCHISINEUCRIS"/&gt;
 *     &lt;enumeration value="JudecatoriaCLUJNAPOCA"/&gt;
 *     &lt;enumeration value="JudecatoriaCONSTANTA"/&gt;
 *     &lt;enumeration value="JudecatoriaCORABIA"/&gt;
 *     &lt;enumeration value="JudecatoriaCOSTESTI"/&gt;
 *     &lt;enumeration value="JudecatoriaCRAIOVA"/&gt;
 *     &lt;enumeration value="JudecatoriaCURTEADEARGES"/&gt;
 *     &lt;enumeration value="JudecatoriaDarabani"/&gt;
 *     &lt;enumeration value="JudecatoriaCAREI"/&gt;
 *     &lt;enumeration value="JudecatoriaDEJ"/&gt;
 *     &lt;enumeration value="JudecatoriaDETA"/&gt;
 *     &lt;enumeration value="JudecatoriaDEVA"/&gt;
 *     &lt;enumeration value="JudecatoriaDOROHOI"/&gt;
 *     &lt;enumeration value="JudecatoriaDRAGASANI"/&gt;
 *     &lt;enumeration value="JudecatoriaDRAGOMIRESTI"/&gt;
 *     &lt;enumeration value="JudecatoriaDROBETATURNUSEVERIN"/&gt;
 *     &lt;enumeration value="JudecatoriaFAGARAS"/&gt;
 *     &lt;enumeration value="JudecatoriaFALTICENI"/&gt;
 *     &lt;enumeration value="JudecatoriaFAUREI"/&gt;
 *     &lt;enumeration value="JudecatoriaFETESTI"/&gt;
 *     &lt;enumeration value="JudecatoriaFILIASI"/&gt;
 *     &lt;enumeration value="JudecatoriaFOCSANI"/&gt;
 *     &lt;enumeration value="JudecatoriaGAESTI"/&gt;
 *     &lt;enumeration value="JudecatoriaGALATI"/&gt;
 *     &lt;enumeration value="JudecatoriaGHEORGHENI"/&gt;
 *     &lt;enumeration value="JudecatoriaGHERLA"/&gt;
 *     &lt;enumeration value="JudecatoriaGIURGIU"/&gt;
 *     &lt;enumeration value="JudecatoriaGURAHUMORULUI"/&gt;
 *     &lt;enumeration value="JudecatoriaGURAHONT"/&gt;
 *     &lt;enumeration value="JudecatoriaHARLAU"/&gt;
 *     &lt;enumeration value="JudecatoriaHATEG"/&gt;
 *     &lt;enumeration value="JudecatoriaHOREZU"/&gt;
 *     &lt;enumeration value="JudecatoriaHUEDIN"/&gt;
 *     &lt;enumeration value="JudecatoriaHUNEDOARA"/&gt;
 *     &lt;enumeration value="JudecatoriaHUSI"/&gt;
 *     &lt;enumeration value="JudecatoriaIASI"/&gt;
 *     &lt;enumeration value="JudecatoriaINEU"/&gt;
 *     &lt;enumeration value="JudecatoriaINSURATEI"/&gt;
 *     &lt;enumeration value="JudecatoriaINTORSURABUZAULUI"/&gt;
 *     &lt;enumeration value="JudecatoriaLEHLIUGARA"/&gt;
 *     &lt;enumeration value="JudecatoriaLIPOVA"/&gt;
 *     &lt;enumeration value="JudecatoriaLUDUS"/&gt;
 *     &lt;enumeration value="JudecatoriaLUGOJ"/&gt;
 *     &lt;enumeration value="JudecatoriaMACIN"/&gt;
 *     &lt;enumeration value="JudecatoriaMANGALIA"/&gt;
 *     &lt;enumeration value="JudecatoriaMARGHITA"/&gt;
 *     &lt;enumeration value="JudecatoriaMEDGIDIA"/&gt;
 *     &lt;enumeration value="JudecatoriaMEDIAS"/&gt;
 *     &lt;enumeration value="JudecatoriaMIERCUREACIUC"/&gt;
 *     &lt;enumeration value="JudecatoriaMIZIL"/&gt;
 *     &lt;enumeration value="JudecatoriaMOINESTI"/&gt;
 *     &lt;enumeration value="JudecatoriaMOLDOVANOUA"/&gt;
 *     &lt;enumeration value="JudecatoriaMORENI"/&gt;
 *     &lt;enumeration value="JudecatoriaMOTRU"/&gt;
 *     &lt;enumeration value="JudecatoriaMURGENI"/&gt;
 *     &lt;enumeration value="JudecatoriaNASAUD"/&gt;
 *     &lt;enumeration value="JudecatoriaNEGRESTIOAS"/&gt;
 *     &lt;enumeration value="JudecatoriaNOVACI"/&gt;
 *     &lt;enumeration value="JudecatoriaODORHEIULSECUIESC"/&gt;
 *     &lt;enumeration value="JudecatoriaOLTENITA"/&gt;
 *     &lt;enumeration value="JudecatoriaONESTI"/&gt;
 *     &lt;enumeration value="JudecatoriaORADEA"/&gt;
 *     &lt;enumeration value="JudecatoriaORASTIE"/&gt;
 *     &lt;enumeration value="JudecatoriaORAVITA"/&gt;
 *     &lt;enumeration value="JudecatoriaORSOVA"/&gt;
 *     &lt;enumeration value="JudecatoriaPANCIU"/&gt;
 *     &lt;enumeration value="JudecatoriaPATARLAGELE"/&gt;
 *     &lt;enumeration value="JudecatoriaPETROSANI"/&gt;
 *     &lt;enumeration value="JudecatoriaPIATRANEAMT"/&gt;
 *     &lt;enumeration value="JudecatoriaPITESTI"/&gt;
 *     &lt;enumeration value="JudecatoriaPLOIESTI"/&gt;
 *     &lt;enumeration value="JudecatoriaPOGOANELE"/&gt;
 *     &lt;enumeration value="JudecatoriaPUCIOASA"/&gt;
 *     &lt;enumeration value="JudecatoriaRACARI"/&gt;
 *     &lt;enumeration value="JudecatoriaRADAUTI"/&gt;
 *     &lt;enumeration value="JudecatoriaRADUCANENI"/&gt;
 *     &lt;enumeration value="JudecatoriaRAMNICUSARAT"/&gt;
 *     &lt;enumeration value="JudecatoriaRAMNICUVALCEA"/&gt;
 *     &lt;enumeration value="JudecatoriaREGHIN"/&gt;
 *     &lt;enumeration value="JudecatoriaRESITA"/&gt;
 *     &lt;enumeration value="JudecatoriaROMAN"/&gt;
 *     &lt;enumeration value="JudecatoriaROSIORIDEVEDE"/&gt;
 *     &lt;enumeration value="JudecatoriaRUPEA"/&gt;
 *     &lt;enumeration value="JudecatoriaSALISTE"/&gt;
 *     &lt;enumeration value="JudecatoriaSANNICOLAULMARE"/&gt;
 *     &lt;enumeration value="JudecatoriaSATUMARE"/&gt;
 *     &lt;enumeration value="JudecatoriaSAVENI"/&gt;
 *     &lt;enumeration value="JudecatoriaSEBES"/&gt;
 *     &lt;enumeration value="JudecatoriaSECTORUL1BUCURESTI"/&gt;
 *     &lt;enumeration value="JudecatoriaSECTORUL2BUCURESTI"/&gt;
 *     &lt;enumeration value="JudecatoriaSECTORUL3BUCURESTI"/&gt;
 *     &lt;enumeration value="JudecatoriaSECTORUL5BUCURESTI"/&gt;
 *     &lt;enumeration value="JudecatoriaSECTORUL6BUCURESTI"/&gt;
 *     &lt;enumeration value="JudecatoriaSEGARCEA"/&gt;
 *     &lt;enumeration value="JudecatoriaSFANTUGHEORGHE"/&gt;
 *     &lt;enumeration value="JudecatoriaSIBIU"/&gt;
 *     &lt;enumeration value="JudecatoriaSIGHETUMARMATIEI"/&gt;
 *     &lt;enumeration value="JudecatoriaSIGHISOARA"/&gt;
 *     &lt;enumeration value="JudecatoriaSIMLEULSILVANIEI"/&gt;
 *     &lt;enumeration value="JudecatoriaSINAIA"/&gt;
 *     &lt;enumeration value="JudecatoriaSLATINA"/&gt;
 *     &lt;enumeration value="JudecatoriaSLOBOZIA"/&gt;
 *     &lt;enumeration value="JudecatoriaSTREHAIA"/&gt;
 *     &lt;enumeration value="JudecatoriaSUCEAVA"/&gt;
 *     &lt;enumeration value="JudecatoriaTARGOVISTE"/&gt;
 *     &lt;enumeration value="JudecatoriaTARGUBUJOR"/&gt;
 *     &lt;enumeration value="JudecatoriaTARGUCARBUNESTI"/&gt;
 *     &lt;enumeration value="JudecatoriaTARGUJIU"/&gt;
 *     &lt;enumeration value="JudecatoriaTARGULAPUS"/&gt;
 *     &lt;enumeration value="JudecatoriaTARGUMURES"/&gt;
 *     &lt;enumeration value="JudecatoriaTARGUNEAMT"/&gt;
 *     &lt;enumeration value="JudecatoriaTARGUSECUIESC"/&gt;
 *     &lt;enumeration value="JudecatoriaTARNAVENI"/&gt;
 *     &lt;enumeration value="JudecatoriaTECUCI"/&gt;
 *     &lt;enumeration value="JudecatoriaTIMISOARA"/&gt;
 *     &lt;enumeration value="JudecatoriaTOPLITA"/&gt;
 *     &lt;enumeration value="JudecatoriaTULCEA"/&gt;
 *     &lt;enumeration value="JudecatoriaTURDA"/&gt;
 *     &lt;enumeration value="JudecatoriaTURNUMAGURELE"/&gt;
 *     &lt;enumeration value="JudecatoriaURZICENI"/&gt;
 *     &lt;enumeration value="JudecatoriaVALENIIDEMUNTE"/&gt;
 *     &lt;enumeration value="JudecatoriaVANJUMARE"/&gt;
 *     &lt;enumeration value="JudecatoriaVASLUI"/&gt;
 *     &lt;enumeration value="JudecatoriaVATRADORNEI"/&gt;
 *     &lt;enumeration value="JudecatoriaVIDELE"/&gt;
 *     &lt;enumeration value="JudecatoriaVISEUDESUS"/&gt;
 *     &lt;enumeration value="JudecatoriaZALAU"/&gt;
 *     &lt;enumeration value="JudecatoriaZARNESTI"/&gt;
 *     &lt;enumeration value="JudecatoriaZIMNICEA"/&gt;
 *     &lt;enumeration value="TribunalulMilitarIASI"/&gt;
 *     &lt;enumeration value="JudecatoriaALEXANDRIA"/&gt;
 *     &lt;enumeration value="TribunalulMilitarTIMISOARA"/&gt;
 *     &lt;enumeration value="TribunalulMilitarCLUJNAPOCA"/&gt;
 *     &lt;enumeration value="TribunalulMilitarTeritorialBUCURESTI"/&gt;
 *     &lt;enumeration value="JudecatoriaAVRIG"/&gt;
 *     &lt;enumeration value="JudecatoriaTOPOLOVENI"/&gt;
 *     &lt;enumeration value="JudecatoriaPODUTURCULUI"/&gt;
 *     &lt;enumeration value="JudecatoriaFAGET"/&gt;
 *     &lt;enumeration value="JudecatoriaSALONTA"/&gt;
 *     &lt;enumeration value="JudecatoriaLIESTI"/&gt;
 *     &lt;enumeration value="JudecatoriaHARSOVA"/&gt;
 *     &lt;enumeration value="JudecatoriaSOMCUTAMARE"/&gt;
 *     &lt;enumeration value="JudecatoriaPASCANI"/&gt;
 *     &lt;enumeration value="TribunalulComercialARGES"/&gt;
 *     &lt;enumeration value="TribunalulComercialCLUJ"/&gt;
 *     &lt;enumeration value="TribunalulComercialMURES"/&gt;
 *     &lt;enumeration value="TribunalulpentruminoriSifamilieBRASOV"/&gt;
 *     &lt;enumeration value="JudecatoriaCORNETU"/&gt;
 *     &lt;enumeration value="JudecatoriaJIBOU"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(name = "Institutie")
@XmlEnum
public enum Institutie {

    @XmlEnumValue("CurteadeApelBUCURESTI")
    CURTEADE_APEL_BUCURESTI("CurteadeApelBUCURESTI"),
    @XmlEnumValue("TribunalulBUCURESTI")
    TRIBUNALUL_BUCURESTI("TribunalulBUCURESTI"),
    @XmlEnumValue("JudecatoriaSECTORUL4BUCURESTI")
    JUDECATORIA_SECTORUL_4_BUCURESTI("JudecatoriaSECTORUL4BUCURESTI"),
    @XmlEnumValue("TribunalulTIMIS")
    TRIBUNALUL_TIMIS("TribunalulTIMIS"),
    @XmlEnumValue("CurteadeApelBACAU")
    CURTEADE_APEL_BACAU("CurteadeApelBACAU"),
    @XmlEnumValue("CurteadeApelCLUJ")
    CURTEADE_APEL_CLUJ("CurteadeApelCLUJ"),
    @XmlEnumValue("CurteadeApelORADEA")
    CURTEADE_APEL_ORADEA("CurteadeApelORADEA"),
    @XmlEnumValue("CurteadeApelCONSTANTA")
    CURTEADE_APEL_CONSTANTA("CurteadeApelCONSTANTA"),
    @XmlEnumValue("CurteadeApelSUCEAVA")
    CURTEADE_APEL_SUCEAVA("CurteadeApelSUCEAVA"),
    @XmlEnumValue("TribunalulBOTOSANI")
    TRIBUNALUL_BOTOSANI("TribunalulBOTOSANI"),
    @XmlEnumValue("CurteadeApelPLOIESTI")
    CURTEADE_APEL_PLOIESTI("CurteadeApelPLOIESTI"),
    @XmlEnumValue("CurteadeApelTARGUMURES")
    CURTEADE_APEL_TARGUMURES("CurteadeApelTARGUMURES"),
    @XmlEnumValue("CurteadeApelGALATI")
    CURTEADE_APEL_GALATI("CurteadeApelGALATI"),
    @XmlEnumValue("CurteadeApelIASI")
    CURTEADE_APEL_IASI("CurteadeApelIASI"),
    @XmlEnumValue("CurteadeApelPITESTI")
    CURTEADE_APEL_PITESTI("CurteadeApelPITESTI"),
    @XmlEnumValue("CurteadeApelCRAIOVA")
    CURTEADE_APEL_CRAIOVA("CurteadeApelCRAIOVA"),
    @XmlEnumValue("JudecatoriaARAD")
    JUDECATORIA_ARAD("JudecatoriaARAD"),
    @XmlEnumValue("CurteadeApelALBAIULIA")
    CURTEADE_APEL_ALBAIULIA("CurteadeApelALBAIULIA"),
    @XmlEnumValue("CurteadeApelTIMISOARA")
    CURTEADE_APEL_TIMISOARA("CurteadeApelTIMISOARA"),
    @XmlEnumValue("TribunalulBRASOV")
    TRIBUNALUL_BRASOV("TribunalulBRASOV"),
    @XmlEnumValue("TribunalulDOLJ")
    TRIBUNALUL_DOLJ("TribunalulDOLJ"),
    @XmlEnumValue("CurteadeApelBRASOV")
    CURTEADE_APEL_BRASOV("CurteadeApelBRASOV"),
    @XmlEnumValue("CurteaMilitaradeApelBUCURESTI")
    CURTEA_MILITARADE_APEL_BUCURESTI("CurteaMilitaradeApelBUCURESTI"),
    @XmlEnumValue("TribunalulSATUMARE")
    TRIBUNALUL_SATUMARE("TribunalulSATUMARE"),
    @XmlEnumValue("TribunalulSALAJ")
    TRIBUNALUL_SALAJ("TribunalulSALAJ"),
    @XmlEnumValue("TribunalulSIBIU")
    TRIBUNALUL_SIBIU("TribunalulSIBIU"),
    @XmlEnumValue("TribunalulSUCEAVA")
    TRIBUNALUL_SUCEAVA("TribunalulSUCEAVA"),
    @XmlEnumValue("TribunalulTELEORMAN")
    TRIBUNALUL_TELEORMAN("TribunalulTELEORMAN"),
    @XmlEnumValue("TribunalulTULCEA")
    TRIBUNALUL_TULCEA("TribunalulTULCEA"),
    @XmlEnumValue("TribunalulVASLUI")
    TRIBUNALUL_VASLUI("TribunalulVASLUI"),
    @XmlEnumValue("TribunalulVALCEA")
    TRIBUNALUL_VALCEA("TribunalulVALCEA"),
    @XmlEnumValue("TribunalulVRANCEA")
    TRIBUNALUL_VRANCEA("TribunalulVRANCEA"),
    @XmlEnumValue("TribunalulMilitarBUCURESTI")
    TRIBUNALUL_MILITAR_BUCURESTI("TribunalulMilitarBUCURESTI"),
    @XmlEnumValue("TribunalulILFOV")
    TRIBUNALUL_ILFOV("TribunalulILFOV"),
    @XmlEnumValue("JudecatoriaBUFTEA")
    JUDECATORIA_BUFTEA("JudecatoriaBUFTEA"),
    @XmlEnumValue("TribunalulGORJ")
    TRIBUNALUL_GORJ("TribunalulGORJ"),
    @XmlEnumValue("TribunalulHARGHITA")
    TRIBUNALUL_HARGHITA("TribunalulHARGHITA"),
    @XmlEnumValue("TribunalulHUNEDOARA")
    TRIBUNALUL_HUNEDOARA("TribunalulHUNEDOARA"),
    @XmlEnumValue("TribunalulIALOMITA")
    TRIBUNALUL_IALOMITA("TribunalulIALOMITA"),
    @XmlEnumValue("TribunalulIASI")
    TRIBUNALUL_IASI("TribunalulIASI"),
    @XmlEnumValue("TribunalulMARAMURES")
    TRIBUNALUL_MARAMURES("TribunalulMARAMURES"),
    @XmlEnumValue("TribunalulMEHEDINTI")
    TRIBUNALUL_MEHEDINTI("TribunalulMEHEDINTI"),
    @XmlEnumValue("TribunalulMURES")
    TRIBUNALUL_MURES("TribunalulMURES"),
    @XmlEnumValue("TribunalulNEAMT")
    TRIBUNALUL_NEAMT("TribunalulNEAMT"),
    @XmlEnumValue("TribunalulOLT")
    TRIBUNALUL_OLT("TribunalulOLT"),
    @XmlEnumValue("TribunalulPRAHOVA")
    TRIBUNALUL_PRAHOVA("TribunalulPRAHOVA"),
    @XmlEnumValue("TribunalulALBA")
    TRIBUNALUL_ALBA("TribunalulALBA"),
    @XmlEnumValue("TribunalulARAD")
    TRIBUNALUL_ARAD("TribunalulARAD"),
    @XmlEnumValue("TribunalulARGES")
    TRIBUNALUL_ARGES("TribunalulARGES"),
    @XmlEnumValue("TribunalulBACAU")
    TRIBUNALUL_BACAU("TribunalulBACAU"),
    @XmlEnumValue("TribunalulBIHOR")
    TRIBUNALUL_BIHOR("TribunalulBIHOR"),
    @XmlEnumValue("TribunalulBISTRITANASAUD")
    TRIBUNALUL_BISTRITANASAUD("TribunalulBISTRITANASAUD"),
    @XmlEnumValue("TribunalulBRAILA")
    TRIBUNALUL_BRAILA("TribunalulBRAILA"),
    @XmlEnumValue("TribunalulBUZAU")
    TRIBUNALUL_BUZAU("TribunalulBUZAU"),
    @XmlEnumValue("TribunalulCARASSEVERIN")
    TRIBUNALUL_CARASSEVERIN("TribunalulCARASSEVERIN"),
    @XmlEnumValue("TribunalulCALARASI")
    TRIBUNALUL_CALARASI("TribunalulCALARASI"),
    @XmlEnumValue("TribunalulCLUJ")
    TRIBUNALUL_CLUJ("TribunalulCLUJ"),
    @XmlEnumValue("TribunalulCONSTANTA")
    TRIBUNALUL_CONSTANTA("TribunalulCONSTANTA"),
    @XmlEnumValue("TribunalulCOVASNA")
    TRIBUNALUL_COVASNA("TribunalulCOVASNA"),
    @XmlEnumValue("TribunalulDAMBOVITA")
    TRIBUNALUL_DAMBOVITA("TribunalulDAMBOVITA"),
    @XmlEnumValue("TribunalulGALATI")
    TRIBUNALUL_GALATI("TribunalulGALATI"),
    @XmlEnumValue("TribunalulGIURGIU")
    TRIBUNALUL_GIURGIU("TribunalulGIURGIU"),
    @XmlEnumValue("JudecatoriaADJUD")
    JUDECATORIA_ADJUD("JudecatoriaADJUD"),
    @XmlEnumValue("JudecatoriaAGNITA")
    JUDECATORIA_AGNITA("JudecatoriaAGNITA"),
    @XmlEnumValue("JudecatoriaAIUD")
    JUDECATORIA_AIUD("JudecatoriaAIUD"),
    @XmlEnumValue("JudecatoriaALBAIULIA")
    JUDECATORIA_ALBAIULIA("JudecatoriaALBAIULIA"),
    @XmlEnumValue("JudecatoriaALESD")
    JUDECATORIA_ALESD("JudecatoriaALESD"),
    @XmlEnumValue("JudecatoriaBABADAG")
    JUDECATORIA_BABADAG("JudecatoriaBABADAG"),
    @XmlEnumValue("JudecatoriaBACAU")
    JUDECATORIA_BACAU("JudecatoriaBACAU"),
    @XmlEnumValue("JudecatoriaBAIADEARAMA")
    JUDECATORIA_BAIADEARAMA("JudecatoriaBAIADEARAMA"),
    @XmlEnumValue("JudecatoriaBAIAMARE")
    JUDECATORIA_BAIAMARE("JudecatoriaBAIAMARE"),
    @XmlEnumValue("JudecatoriaBAILESTI")
    JUDECATORIA_BAILESTI("JudecatoriaBAILESTI"),
    @XmlEnumValue("JudecatoriaBALS")
    JUDECATORIA_BALS("JudecatoriaBALS"),
    @XmlEnumValue("JudecatoriaBALCESTI")
    JUDECATORIA_BALCESTI("JudecatoriaBALCESTI"),
    @XmlEnumValue("JudecatoriaBECLEAN")
    JUDECATORIA_BECLEAN("JudecatoriaBECLEAN"),
    @XmlEnumValue("JudecatoriaBEIUS")
    JUDECATORIA_BEIUS("JudecatoriaBEIUS"),
    @XmlEnumValue("JudecatoriaBICAZ")
    JUDECATORIA_BICAZ("JudecatoriaBICAZ"),
    @XmlEnumValue("JudecatoriaBARLAD")
    JUDECATORIA_BARLAD("JudecatoriaBARLAD"),
    @XmlEnumValue("JudecatoriaBISTRITA")
    JUDECATORIA_BISTRITA("JudecatoriaBISTRITA"),
    @XmlEnumValue("JudecatoriaBLAJ")
    JUDECATORIA_BLAJ("JudecatoriaBLAJ"),
    @XmlEnumValue("JudecatoriaBOLINTINVALE")
    JUDECATORIA_BOLINTINVALE("JudecatoriaBOLINTINVALE"),
    @XmlEnumValue("JudecatoriaBOTOSANI")
    JUDECATORIA_BOTOSANI("JudecatoriaBOTOSANI"),
    @XmlEnumValue("JudecatoriaBOZOVICI")
    JUDECATORIA_BOZOVICI("JudecatoriaBOZOVICI"),
    @XmlEnumValue("JudecatoriaBRAD")
    JUDECATORIA_BRAD("JudecatoriaBRAD"),
    @XmlEnumValue("JudecatoriaBRAILA")
    JUDECATORIA_BRAILA("JudecatoriaBRAILA"),
    @XmlEnumValue("JudecatoriaBRASOV")
    JUDECATORIA_BRASOV("JudecatoriaBRASOV"),
    @XmlEnumValue("JudecatoriaBREZOI")
    JUDECATORIA_BREZOI("JudecatoriaBREZOI"),
    @XmlEnumValue("JudecatoriaBUHUSI")
    JUDECATORIA_BUHUSI("JudecatoriaBUHUSI"),
    @XmlEnumValue("JudecatoriaBUZAU")
    JUDECATORIA_BUZAU("JudecatoriaBUZAU"),
    @XmlEnumValue("JudecatoriaCALAFAT")
    JUDECATORIA_CALAFAT("JudecatoriaCALAFAT"),
    @XmlEnumValue("JudecatoriaCALARASI")
    JUDECATORIA_CALARASI("JudecatoriaCALARASI"),
    @XmlEnumValue("JudecatoriaCAMPENI")
    JUDECATORIA_CAMPENI("JudecatoriaCAMPENI"),
    @XmlEnumValue("JudecatoriaCAMPINA")
    JUDECATORIA_CAMPINA("JudecatoriaCAMPINA"),
    @XmlEnumValue("JudecatoriaCAMPULUNG")
    JUDECATORIA_CAMPULUNG("JudecatoriaCAMPULUNG"),
    @XmlEnumValue("JudecatoriaCAMPULUNGMOLDOVENESC")
    JUDECATORIA_CAMPULUNGMOLDOVENESC("JudecatoriaCAMPULUNGMOLDOVENESC"),
    @XmlEnumValue("JudecatoriaCARACAL")
    JUDECATORIA_CARACAL("JudecatoriaCARACAL"),
    @XmlEnumValue("JudecatoriaCARANSEBES")
    JUDECATORIA_CARANSEBES("JudecatoriaCARANSEBES"),
    @XmlEnumValue("JudecatoriaCHISINEUCRIS")
    JUDECATORIA_CHISINEUCRIS("JudecatoriaCHISINEUCRIS"),
    @XmlEnumValue("JudecatoriaCLUJNAPOCA")
    JUDECATORIA_CLUJNAPOCA("JudecatoriaCLUJNAPOCA"),
    @XmlEnumValue("JudecatoriaCONSTANTA")
    JUDECATORIA_CONSTANTA("JudecatoriaCONSTANTA"),
    @XmlEnumValue("JudecatoriaCORABIA")
    JUDECATORIA_CORABIA("JudecatoriaCORABIA"),
    @XmlEnumValue("JudecatoriaCOSTESTI")
    JUDECATORIA_COSTESTI("JudecatoriaCOSTESTI"),
    @XmlEnumValue("JudecatoriaCRAIOVA")
    JUDECATORIA_CRAIOVA("JudecatoriaCRAIOVA"),
    @XmlEnumValue("JudecatoriaCURTEADEARGES")
    JUDECATORIA_CURTEADEARGES("JudecatoriaCURTEADEARGES"),
    @XmlEnumValue("JudecatoriaDarabani")
    JUDECATORIA_DARABANI("JudecatoriaDarabani"),
    @XmlEnumValue("JudecatoriaCAREI")
    JUDECATORIA_CAREI("JudecatoriaCAREI"),
    @XmlEnumValue("JudecatoriaDEJ")
    JUDECATORIA_DEJ("JudecatoriaDEJ"),
    @XmlEnumValue("JudecatoriaDETA")
    JUDECATORIA_DETA("JudecatoriaDETA"),
    @XmlEnumValue("JudecatoriaDEVA")
    JUDECATORIA_DEVA("JudecatoriaDEVA"),
    @XmlEnumValue("JudecatoriaDOROHOI")
    JUDECATORIA_DOROHOI("JudecatoriaDOROHOI"),
    @XmlEnumValue("JudecatoriaDRAGASANI")
    JUDECATORIA_DRAGASANI("JudecatoriaDRAGASANI"),
    @XmlEnumValue("JudecatoriaDRAGOMIRESTI")
    JUDECATORIA_DRAGOMIRESTI("JudecatoriaDRAGOMIRESTI"),
    @XmlEnumValue("JudecatoriaDROBETATURNUSEVERIN")
    JUDECATORIA_DROBETATURNUSEVERIN("JudecatoriaDROBETATURNUSEVERIN"),
    @XmlEnumValue("JudecatoriaFAGARAS")
    JUDECATORIA_FAGARAS("JudecatoriaFAGARAS"),
    @XmlEnumValue("JudecatoriaFALTICENI")
    JUDECATORIA_FALTICENI("JudecatoriaFALTICENI"),
    @XmlEnumValue("JudecatoriaFAUREI")
    JUDECATORIA_FAUREI("JudecatoriaFAUREI"),
    @XmlEnumValue("JudecatoriaFETESTI")
    JUDECATORIA_FETESTI("JudecatoriaFETESTI"),
    @XmlEnumValue("JudecatoriaFILIASI")
    JUDECATORIA_FILIASI("JudecatoriaFILIASI"),
    @XmlEnumValue("JudecatoriaFOCSANI")
    JUDECATORIA_FOCSANI("JudecatoriaFOCSANI"),
    @XmlEnumValue("JudecatoriaGAESTI")
    JUDECATORIA_GAESTI("JudecatoriaGAESTI"),
    @XmlEnumValue("JudecatoriaGALATI")
    JUDECATORIA_GALATI("JudecatoriaGALATI"),
    @XmlEnumValue("JudecatoriaGHEORGHENI")
    JUDECATORIA_GHEORGHENI("JudecatoriaGHEORGHENI"),
    @XmlEnumValue("JudecatoriaGHERLA")
    JUDECATORIA_GHERLA("JudecatoriaGHERLA"),
    @XmlEnumValue("JudecatoriaGIURGIU")
    JUDECATORIA_GIURGIU("JudecatoriaGIURGIU"),
    @XmlEnumValue("JudecatoriaGURAHUMORULUI")
    JUDECATORIA_GURAHUMORULUI("JudecatoriaGURAHUMORULUI"),
    @XmlEnumValue("JudecatoriaGURAHONT")
    JUDECATORIA_GURAHONT("JudecatoriaGURAHONT"),
    @XmlEnumValue("JudecatoriaHARLAU")
    JUDECATORIA_HARLAU("JudecatoriaHARLAU"),
    @XmlEnumValue("JudecatoriaHATEG")
    JUDECATORIA_HATEG("JudecatoriaHATEG"),
    @XmlEnumValue("JudecatoriaHOREZU")
    JUDECATORIA_HOREZU("JudecatoriaHOREZU"),
    @XmlEnumValue("JudecatoriaHUEDIN")
    JUDECATORIA_HUEDIN("JudecatoriaHUEDIN"),
    @XmlEnumValue("JudecatoriaHUNEDOARA")
    JUDECATORIA_HUNEDOARA("JudecatoriaHUNEDOARA"),
    @XmlEnumValue("JudecatoriaHUSI")
    JUDECATORIA_HUSI("JudecatoriaHUSI"),
    @XmlEnumValue("JudecatoriaIASI")
    JUDECATORIA_IASI("JudecatoriaIASI"),
    @XmlEnumValue("JudecatoriaINEU")
    JUDECATORIA_INEU("JudecatoriaINEU"),
    @XmlEnumValue("JudecatoriaINSURATEI")
    JUDECATORIA_INSURATEI("JudecatoriaINSURATEI"),
    @XmlEnumValue("JudecatoriaINTORSURABUZAULUI")
    JUDECATORIA_INTORSURABUZAULUI("JudecatoriaINTORSURABUZAULUI"),
    @XmlEnumValue("JudecatoriaLEHLIUGARA")
    JUDECATORIA_LEHLIUGARA("JudecatoriaLEHLIUGARA"),
    @XmlEnumValue("JudecatoriaLIPOVA")
    JUDECATORIA_LIPOVA("JudecatoriaLIPOVA"),
    @XmlEnumValue("JudecatoriaLUDUS")
    JUDECATORIA_LUDUS("JudecatoriaLUDUS"),
    @XmlEnumValue("JudecatoriaLUGOJ")
    JUDECATORIA_LUGOJ("JudecatoriaLUGOJ"),
    @XmlEnumValue("JudecatoriaMACIN")
    JUDECATORIA_MACIN("JudecatoriaMACIN"),
    @XmlEnumValue("JudecatoriaMANGALIA")
    JUDECATORIA_MANGALIA("JudecatoriaMANGALIA"),
    @XmlEnumValue("JudecatoriaMARGHITA")
    JUDECATORIA_MARGHITA("JudecatoriaMARGHITA"),
    @XmlEnumValue("JudecatoriaMEDGIDIA")
    JUDECATORIA_MEDGIDIA("JudecatoriaMEDGIDIA"),
    @XmlEnumValue("JudecatoriaMEDIAS")
    JUDECATORIA_MEDIAS("JudecatoriaMEDIAS"),
    @XmlEnumValue("JudecatoriaMIERCUREACIUC")
    JUDECATORIA_MIERCUREACIUC("JudecatoriaMIERCUREACIUC"),
    @XmlEnumValue("JudecatoriaMIZIL")
    JUDECATORIA_MIZIL("JudecatoriaMIZIL"),
    @XmlEnumValue("JudecatoriaMOINESTI")
    JUDECATORIA_MOINESTI("JudecatoriaMOINESTI"),
    @XmlEnumValue("JudecatoriaMOLDOVANOUA")
    JUDECATORIA_MOLDOVANOUA("JudecatoriaMOLDOVANOUA"),
    @XmlEnumValue("JudecatoriaMORENI")
    JUDECATORIA_MORENI("JudecatoriaMORENI"),
    @XmlEnumValue("JudecatoriaMOTRU")
    JUDECATORIA_MOTRU("JudecatoriaMOTRU"),
    @XmlEnumValue("JudecatoriaMURGENI")
    JUDECATORIA_MURGENI("JudecatoriaMURGENI"),
    @XmlEnumValue("JudecatoriaNASAUD")
    JUDECATORIA_NASAUD("JudecatoriaNASAUD"),
    @XmlEnumValue("JudecatoriaNEGRESTIOAS")
    JUDECATORIA_NEGRESTIOAS("JudecatoriaNEGRESTIOAS"),
    @XmlEnumValue("JudecatoriaNOVACI")
    JUDECATORIA_NOVACI("JudecatoriaNOVACI"),
    @XmlEnumValue("JudecatoriaODORHEIULSECUIESC")
    JUDECATORIA_ODORHEIULSECUIESC("JudecatoriaODORHEIULSECUIESC"),
    @XmlEnumValue("JudecatoriaOLTENITA")
    JUDECATORIA_OLTENITA("JudecatoriaOLTENITA"),
    @XmlEnumValue("JudecatoriaONESTI")
    JUDECATORIA_ONESTI("JudecatoriaONESTI"),
    @XmlEnumValue("JudecatoriaORADEA")
    JUDECATORIA_ORADEA("JudecatoriaORADEA"),
    @XmlEnumValue("JudecatoriaORASTIE")
    JUDECATORIA_ORASTIE("JudecatoriaORASTIE"),
    @XmlEnumValue("JudecatoriaORAVITA")
    JUDECATORIA_ORAVITA("JudecatoriaORAVITA"),
    @XmlEnumValue("JudecatoriaORSOVA")
    JUDECATORIA_ORSOVA("JudecatoriaORSOVA"),
    @XmlEnumValue("JudecatoriaPANCIU")
    JUDECATORIA_PANCIU("JudecatoriaPANCIU"),
    @XmlEnumValue("JudecatoriaPATARLAGELE")
    JUDECATORIA_PATARLAGELE("JudecatoriaPATARLAGELE"),
    @XmlEnumValue("JudecatoriaPETROSANI")
    JUDECATORIA_PETROSANI("JudecatoriaPETROSANI"),
    @XmlEnumValue("JudecatoriaPIATRANEAMT")
    JUDECATORIA_PIATRANEAMT("JudecatoriaPIATRANEAMT"),
    @XmlEnumValue("JudecatoriaPITESTI")
    JUDECATORIA_PITESTI("JudecatoriaPITESTI"),
    @XmlEnumValue("JudecatoriaPLOIESTI")
    JUDECATORIA_PLOIESTI("JudecatoriaPLOIESTI"),
    @XmlEnumValue("JudecatoriaPOGOANELE")
    JUDECATORIA_POGOANELE("JudecatoriaPOGOANELE"),
    @XmlEnumValue("JudecatoriaPUCIOASA")
    JUDECATORIA_PUCIOASA("JudecatoriaPUCIOASA"),
    @XmlEnumValue("JudecatoriaRACARI")
    JUDECATORIA_RACARI("JudecatoriaRACARI"),
    @XmlEnumValue("JudecatoriaRADAUTI")
    JUDECATORIA_RADAUTI("JudecatoriaRADAUTI"),
    @XmlEnumValue("JudecatoriaRADUCANENI")
    JUDECATORIA_RADUCANENI("JudecatoriaRADUCANENI"),
    @XmlEnumValue("JudecatoriaRAMNICUSARAT")
    JUDECATORIA_RAMNICUSARAT("JudecatoriaRAMNICUSARAT"),
    @XmlEnumValue("JudecatoriaRAMNICUVALCEA")
    JUDECATORIA_RAMNICUVALCEA("JudecatoriaRAMNICUVALCEA"),
    @XmlEnumValue("JudecatoriaREGHIN")
    JUDECATORIA_REGHIN("JudecatoriaREGHIN"),
    @XmlEnumValue("JudecatoriaRESITA")
    JUDECATORIA_RESITA("JudecatoriaRESITA"),
    @XmlEnumValue("JudecatoriaROMAN")
    JUDECATORIA_ROMAN("JudecatoriaROMAN"),
    @XmlEnumValue("JudecatoriaROSIORIDEVEDE")
    JUDECATORIA_ROSIORIDEVEDE("JudecatoriaROSIORIDEVEDE"),
    @XmlEnumValue("JudecatoriaRUPEA")
    JUDECATORIA_RUPEA("JudecatoriaRUPEA"),
    @XmlEnumValue("JudecatoriaSALISTE")
    JUDECATORIA_SALISTE("JudecatoriaSALISTE"),
    @XmlEnumValue("JudecatoriaSANNICOLAULMARE")
    JUDECATORIA_SANNICOLAULMARE("JudecatoriaSANNICOLAULMARE"),
    @XmlEnumValue("JudecatoriaSATUMARE")
    JUDECATORIA_SATUMARE("JudecatoriaSATUMARE"),
    @XmlEnumValue("JudecatoriaSAVENI")
    JUDECATORIA_SAVENI("JudecatoriaSAVENI"),
    @XmlEnumValue("JudecatoriaSEBES")
    JUDECATORIA_SEBES("JudecatoriaSEBES"),
    @XmlEnumValue("JudecatoriaSECTORUL1BUCURESTI")
    JUDECATORIA_SECTORUL_1_BUCURESTI("JudecatoriaSECTORUL1BUCURESTI"),
    @XmlEnumValue("JudecatoriaSECTORUL2BUCURESTI")
    JUDECATORIA_SECTORUL_2_BUCURESTI("JudecatoriaSECTORUL2BUCURESTI"),
    @XmlEnumValue("JudecatoriaSECTORUL3BUCURESTI")
    JUDECATORIA_SECTORUL_3_BUCURESTI("JudecatoriaSECTORUL3BUCURESTI"),
    @XmlEnumValue("JudecatoriaSECTORUL5BUCURESTI")
    JUDECATORIA_SECTORUL_5_BUCURESTI("JudecatoriaSECTORUL5BUCURESTI"),
    @XmlEnumValue("JudecatoriaSECTORUL6BUCURESTI")
    JUDECATORIA_SECTORUL_6_BUCURESTI("JudecatoriaSECTORUL6BUCURESTI"),
    @XmlEnumValue("JudecatoriaSEGARCEA")
    JUDECATORIA_SEGARCEA("JudecatoriaSEGARCEA"),
    @XmlEnumValue("JudecatoriaSFANTUGHEORGHE")
    JUDECATORIA_SFANTUGHEORGHE("JudecatoriaSFANTUGHEORGHE"),
    @XmlEnumValue("JudecatoriaSIBIU")
    JUDECATORIA_SIBIU("JudecatoriaSIBIU"),
    @XmlEnumValue("JudecatoriaSIGHETUMARMATIEI")
    JUDECATORIA_SIGHETUMARMATIEI("JudecatoriaSIGHETUMARMATIEI"),
    @XmlEnumValue("JudecatoriaSIGHISOARA")
    JUDECATORIA_SIGHISOARA("JudecatoriaSIGHISOARA"),
    @XmlEnumValue("JudecatoriaSIMLEULSILVANIEI")
    JUDECATORIA_SIMLEULSILVANIEI("JudecatoriaSIMLEULSILVANIEI"),
    @XmlEnumValue("JudecatoriaSINAIA")
    JUDECATORIA_SINAIA("JudecatoriaSINAIA"),
    @XmlEnumValue("JudecatoriaSLATINA")
    JUDECATORIA_SLATINA("JudecatoriaSLATINA"),
    @XmlEnumValue("JudecatoriaSLOBOZIA")
    JUDECATORIA_SLOBOZIA("JudecatoriaSLOBOZIA"),
    @XmlEnumValue("JudecatoriaSTREHAIA")
    JUDECATORIA_STREHAIA("JudecatoriaSTREHAIA"),
    @XmlEnumValue("JudecatoriaSUCEAVA")
    JUDECATORIA_SUCEAVA("JudecatoriaSUCEAVA"),
    @XmlEnumValue("JudecatoriaTARGOVISTE")
    JUDECATORIA_TARGOVISTE("JudecatoriaTARGOVISTE"),
    @XmlEnumValue("JudecatoriaTARGUBUJOR")
    JUDECATORIA_TARGUBUJOR("JudecatoriaTARGUBUJOR"),
    @XmlEnumValue("JudecatoriaTARGUCARBUNESTI")
    JUDECATORIA_TARGUCARBUNESTI("JudecatoriaTARGUCARBUNESTI"),
    @XmlEnumValue("JudecatoriaTARGUJIU")
    JUDECATORIA_TARGUJIU("JudecatoriaTARGUJIU"),
    @XmlEnumValue("JudecatoriaTARGULAPUS")
    JUDECATORIA_TARGULAPUS("JudecatoriaTARGULAPUS"),
    @XmlEnumValue("JudecatoriaTARGUMURES")
    JUDECATORIA_TARGUMURES("JudecatoriaTARGUMURES"),
    @XmlEnumValue("JudecatoriaTARGUNEAMT")
    JUDECATORIA_TARGUNEAMT("JudecatoriaTARGUNEAMT"),
    @XmlEnumValue("JudecatoriaTARGUSECUIESC")
    JUDECATORIA_TARGUSECUIESC("JudecatoriaTARGUSECUIESC"),
    @XmlEnumValue("JudecatoriaTARNAVENI")
    JUDECATORIA_TARNAVENI("JudecatoriaTARNAVENI"),
    @XmlEnumValue("JudecatoriaTECUCI")
    JUDECATORIA_TECUCI("JudecatoriaTECUCI"),
    @XmlEnumValue("JudecatoriaTIMISOARA")
    JUDECATORIA_TIMISOARA("JudecatoriaTIMISOARA"),
    @XmlEnumValue("JudecatoriaTOPLITA")
    JUDECATORIA_TOPLITA("JudecatoriaTOPLITA"),
    @XmlEnumValue("JudecatoriaTULCEA")
    JUDECATORIA_TULCEA("JudecatoriaTULCEA"),
    @XmlEnumValue("JudecatoriaTURDA")
    JUDECATORIA_TURDA("JudecatoriaTURDA"),
    @XmlEnumValue("JudecatoriaTURNUMAGURELE")
    JUDECATORIA_TURNUMAGURELE("JudecatoriaTURNUMAGURELE"),
    @XmlEnumValue("JudecatoriaURZICENI")
    JUDECATORIA_URZICENI("JudecatoriaURZICENI"),
    @XmlEnumValue("JudecatoriaVALENIIDEMUNTE")
    JUDECATORIA_VALENIIDEMUNTE("JudecatoriaVALENIIDEMUNTE"),
    @XmlEnumValue("JudecatoriaVANJUMARE")
    JUDECATORIA_VANJUMARE("JudecatoriaVANJUMARE"),
    @XmlEnumValue("JudecatoriaVASLUI")
    JUDECATORIA_VASLUI("JudecatoriaVASLUI"),
    @XmlEnumValue("JudecatoriaVATRADORNEI")
    JUDECATORIA_VATRADORNEI("JudecatoriaVATRADORNEI"),
    @XmlEnumValue("JudecatoriaVIDELE")
    JUDECATORIA_VIDELE("JudecatoriaVIDELE"),
    @XmlEnumValue("JudecatoriaVISEUDESUS")
    JUDECATORIA_VISEUDESUS("JudecatoriaVISEUDESUS"),
    @XmlEnumValue("JudecatoriaZALAU")
    JUDECATORIA_ZALAU("JudecatoriaZALAU"),
    @XmlEnumValue("JudecatoriaZARNESTI")
    JUDECATORIA_ZARNESTI("JudecatoriaZARNESTI"),
    @XmlEnumValue("JudecatoriaZIMNICEA")
    JUDECATORIA_ZIMNICEA("JudecatoriaZIMNICEA"),
    @XmlEnumValue("TribunalulMilitarIASI")
    TRIBUNALUL_MILITAR_IASI("TribunalulMilitarIASI"),
    @XmlEnumValue("JudecatoriaALEXANDRIA")
    JUDECATORIA_ALEXANDRIA("JudecatoriaALEXANDRIA"),
    @XmlEnumValue("TribunalulMilitarTIMISOARA")
    TRIBUNALUL_MILITAR_TIMISOARA("TribunalulMilitarTIMISOARA"),
    @XmlEnumValue("TribunalulMilitarCLUJNAPOCA")
    TRIBUNALUL_MILITAR_CLUJNAPOCA("TribunalulMilitarCLUJNAPOCA"),
    @XmlEnumValue("TribunalulMilitarTeritorialBUCURESTI")
    TRIBUNALUL_MILITAR_TERITORIAL_BUCURESTI("TribunalulMilitarTeritorialBUCURESTI"),
    @XmlEnumValue("JudecatoriaAVRIG")
    JUDECATORIA_AVRIG("JudecatoriaAVRIG"),
    @XmlEnumValue("JudecatoriaTOPOLOVENI")
    JUDECATORIA_TOPOLOVENI("JudecatoriaTOPOLOVENI"),
    @XmlEnumValue("JudecatoriaPODUTURCULUI")
    JUDECATORIA_PODUTURCULUI("JudecatoriaPODUTURCULUI"),
    @XmlEnumValue("JudecatoriaFAGET")
    JUDECATORIA_FAGET("JudecatoriaFAGET"),
    @XmlEnumValue("JudecatoriaSALONTA")
    JUDECATORIA_SALONTA("JudecatoriaSALONTA"),
    @XmlEnumValue("JudecatoriaLIESTI")
    JUDECATORIA_LIESTI("JudecatoriaLIESTI"),
    @XmlEnumValue("JudecatoriaHARSOVA")
    JUDECATORIA_HARSOVA("JudecatoriaHARSOVA"),
    @XmlEnumValue("JudecatoriaSOMCUTAMARE")
    JUDECATORIA_SOMCUTAMARE("JudecatoriaSOMCUTAMARE"),
    @XmlEnumValue("JudecatoriaPASCANI")
    JUDECATORIA_PASCANI("JudecatoriaPASCANI"),
    @XmlEnumValue("TribunalulComercialARGES")
    TRIBUNALUL_COMERCIAL_ARGES("TribunalulComercialARGES"),
    @XmlEnumValue("TribunalulComercialCLUJ")
    TRIBUNALUL_COMERCIAL_CLUJ("TribunalulComercialCLUJ"),
    @XmlEnumValue("TribunalulComercialMURES")
    TRIBUNALUL_COMERCIAL_MURES("TribunalulComercialMURES"),
    @XmlEnumValue("TribunalulpentruminoriSifamilieBRASOV")
    TRIBUNALULPENTRUMINORI_SIFAMILIE_BRASOV("TribunalulpentruminoriSifamilieBRASOV"),
    @XmlEnumValue("JudecatoriaCORNETU")
    JUDECATORIA_CORNETU("JudecatoriaCORNETU"),
    @XmlEnumValue("JudecatoriaJIBOU")
    JUDECATORIA_JIBOU("JudecatoriaJIBOU");
    private final String value;

    Institutie(String v) {
        value = v;
    }

    public static Institutie fromValue(String v) {
        for (Institutie c : Institutie.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

    public String value() {
        return value;
    }

}
