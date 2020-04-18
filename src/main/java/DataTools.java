import JsonSchemas.*;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFacade;
//import org.dozer.DozerBeanMapper;
//import org.dozer.Mapper;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class DataTools {

    public static String  getUserIdByName(ArrayList<UserRepresentation> users, String userName) {
        String sRes = "";
        for(UserRepresentation user : users) {
            if(user.getUsername().compareTo(userName) == 0) {
                sRes = user.getId();
                break;
            }
        }
        return(sRes);
    }

    public static void map01() {

        ClassSrc01 src = new ClassSrc01();
        src.setFirstName("Zdenek");
        src.setLastName("Klar");
        src.setUserame("zdenek-klar");
        src.setEnabled(true);
        src.setEmailVerified(false);
        src.setI1(11);
        src.setI2(222);
        src.setL1(1111);
        src.setL2(2222);
        src.setL3(3333);

        classDst01 dst2 = new classDst01();
        try {
            FieldMapper2.copy(src, dst2);
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }

        int iii = dst2.getI2();



        UserRepresentation user = new UserRepresentation();
        RtlUser r = new RtlUser();
        try {
            FieldMapper2.copy(user, r);
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }




        classSrc02 src1 = new classSrc02();
        src1.firstName="Zdenek";
        src1.lastName="Klar";
        src1.userame="zdenek-klar";
        src1.enabled=true;
        src1.emailVerified=false;
        src1.i1=11;
        src1.i2=222;
        src1.l1=1111;
        src1.l2=2222;
        src1.l3=3333;


        //Mapper mapper = new DozerBeanMapper();
        //classDst01 dst = mapper.map(src, classDst01.class);
        //classDst01 dst = new classDst01();
        //mapper.map(src, dst);

        //BoundMapperFacade<ClassSrc01, classDst01> boundMapper =
        //        mapperFactory.getMapperFacade(ClassSrc01.class, classDst01.class);
        //MapperFacade mapper = mapperFactory.getMapperFacade();

        classDst02 dst = new classDst02();
        try {
            FieldMapper.copy(src1, dst);
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }

        int ii = dst.i2;


    }

}
