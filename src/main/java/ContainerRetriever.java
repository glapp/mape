import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;

import java.util.List;

/**
 * Created by riccardo on 16.02.16.
 */
public class ContainerRetriever {

    public static void main (String[] args) {

        DockerClientConfig config = DockerClientConfig.createDefaultConfigBuilder()
                .withUri("https://192.168.99.101:3376")
                .withDockerCertPath("/home/riccardo/.docker/machine/certs")
                .build();

        DockerClient docker = DockerClientBuilder.getInstance(config).build();

        //Info info = docker.infoCmd().exec();
        List<Container> containers = docker.listContainersCmd().withShowAll(true).exec();

        for (Container c : containers) {
            System.out.println(c.getImage());
        }

        //System.out.println(info.getImages());


    }

}