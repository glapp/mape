import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.jaxrs.DockerCmdExecFactoryImpl;

import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;

/**
 * Created by riccardo on 16.02.16.
 */
public class DockerRetriever {

    final static Logger logger = Logger.getLogger(DockerRetriever.class);

    public static void main (String[] args) {

        DockerClientConfig config = DockerClientConfig.createDefaultConfigBuilder()
                .withUri("https://192.168.99.101:3376") // 3376 = Swarm
                .withDockerCertPath("/home/riccardo/.docker/machine/certs")
                .build();

        DockerCmdExecFactoryImpl dockerCmdExecFactory = new DockerCmdExecFactoryImpl()
                .withReadTimeout(1000)
                .withConnectTimeout(1000)
                .withMaxTotalConnections(100)
                .withMaxPerRouteConnections(10);

        DockerClient dockerClient = DockerClientBuilder.getInstance(config)
                .withDockerCmdExecFactory(dockerCmdExecFactory)
                .build();

        // Docker info:
        Info info = dockerClient.infoCmd().exec();
        System.out.println("DOCKER INFO: " + info);
        System.out.println("Node URI: " + config.getUri());
        System.out.println("Info name: "+info.getName());
        System.out.println("Info kernelVersion: " + info.getKernelVersion());
        System.out.println();


        // Image info:
        List<Image> images = dockerClient.listImagesCmd().exec();
        System.out.println("IMAGES: " + images);
        System.out.println("Number of images: " + info.getImages());
        // todo: number of images is incorrect! Number was 2 more than the images listed.

        for (Image i : images) {
            //logger.info("id=" +i.getId() + " ----- repoTags=" + Arrays.toString(i.getRepoTags()));
            System.out.println("id= " +i.getId()
                    + " \t repoTags= " + Arrays.toString(i.getRepoTags())
                    + "\t size= " + i.getSize());
        }
        System.out.println();


        // Container info:
        List<Container> containers = dockerClient.listContainersCmd().withShowAll(true).exec();
        System.out.println("CONTAINERS: "+containers);
        System.out.println("Number of containers: " + info.getContainers());

        for (Container c : containers) {
            //logger.info("listContainer: id=" + c.getId() + " ----- image=" + c.getImage() + " ----- names="
            //        +Arrays.toString(c.getNames()));
            System.out.println("id= " + c.getId()
                    + " \t image= " + c.getImage()
                    + " \t name= " + Arrays.toString(c.getNames())
                    + " \t\t status= " + c.getStatus());
        }

    }

}