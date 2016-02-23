# README #

### What is this repository for? ###

Example output of ContainerRetriever class:


Node URI: https://192.168.99.101:3376
Info name: swarm-master
Info kernelVersion: 4.1.18-boot2docker

id= sha256:a0cb29e15574742469606b14a540c2c36ff74d682e4a1a00bc332492d788b84a 	 repoTags= [examplevotingapp_worker:latest]	 size= 706010576
id= sha256:e0f845eead0c6d16b94e565f0a571038abb6637936a86f61cb01d6ee94f91684 	 repoTags= [examplevotingapp_result-app:latest]	 size= 650063749
id= sha256:3a9c17de200a63be27da510c26736df9a18812d6efe0905ea55dd807e06b9166 	 repoTags= [examplevotingapp_voting-app:latest]	 size= 683026612
id= sha256:d1c2b663d66adbc7c547f0452fe29b940696cf066334cd5f33f794a7108357cb 	 repoTags= [swarm:latest]	 size= 18107315
id= sha256:dbcaac341861b155b3b601fc501a69f71588246de263a6d7569ae593168b126c 	 repoTags= [python:2.7]	 size= 676236480
id= sha256:e4040707340cf4432e46fe4822d9dbf1bc369125cf7edd4ce782b466582e9e4b 	 repoTags= [node:0.10]	 size= 633783203
id= sha256:437c0da221aeaeec27596eda53a5dc201a014731ba7ff6ec645519a1acd50156 	 repoTags= [java:7]	 size= 587813471
id= sha256:fb46ec1d66e0838cf043ec85d6f1933fbbb18df4f093cb3248bf657532b80b59 	 repoTags= [redis:latest]	 size= 151331474
id= sha256:ad2fc7b9d681789490dfc6b91ef446fc23268572df328158ab542255311a7359 	 repoTags= [postgres:9.4]	 size= 263136747
id= sha256:690ed74de00f99a7d00a98a5ad855ac4febd66412be132438f9b8dbd300a937d 	 repoTags= [hello-world:latest]	 size= 960

Number of containers: 10
id= fef0f05d63b02842fac2cc4ad1d54f241197014844d59b5a4eb679de5ed26c1e 	 image= examplevotingapp_worker 	 name= [/swarm-agent-01/examplevotingapp_worker_1] 		 status= Up 6 minutes
id= 56309385fb35434e4e433bd3df379e2dc3c20273c269e36c19e3fe250f0b197a 	 image= redis 	 name= [/swarm-agent-02/examplevotingapp_redis_1] 		 status= Up 10 minutes
id= 7aa464b3ae6dec14949396484bf69a48a02f3b15afce103d111339b1711e616a 	 image= postgres:9.4 	 name= [/swarm-agent-01/examplevotingapp_db_1] 		 status= Up 11 minutes
id= b908fa6ba6d0cf595b1462fa8427bb5f91e05ca1ae1368208bae248e4fb4d57e 	 image= examplevotingapp_result-app 	 name= [/swarm-master/examplevotingapp_result-app_1] 		 status= Up 12 minutes
id= 58ce7269fd3ee31169c27f2868b6622cb9402556542c522b7b178544fa7de97f 	 image= examplevotingapp_voting-app 	 name= [/swarm-agent-01/examplevotingapp_voting-app_1] 		 status= Up 13 minutes
id= 239f7fe13742147826014f7e7602414bebe5b540cc8b1a45b481c78d89ac2a51 	 image= hello-world 	 name= [/swarm-agent-02/romantic_euclid] 		 status= Exited (0) About an hour ago
id= 19a771a13e1cc873e409868b06187e01db0e69758309634a4c35b423ff94001b 	 image= swarm:latest 	 name= [/swarm-agent-02/swarm-agent] 		 status= Up 2 hours
id= 0f6cafd6d2c7c2078b58228b9effd7545ca6453c0bd214559f2d793c4eed0808 	 image= swarm:latest 	 name= [/swarm-agent-01/swarm-agent] 		 status= Up 2 hours
id= fff3da047615b24d3154b45856525f14f80fbfa59ce0f866846874191198021f 	 image= swarm:latest 	 name= [/swarm-master/swarm-agent] 		 status= Up 2 hours
id= 2b62d534f9f14c43ac5a126a60ab3745472fbeed814af833b7e6294049cb1c5c 	 image= swarm:latest 	 name= [/swarm-master/swarm-agent-master] 		 status= Up 2 hours

Process finished with exit code 0



### How do I get set up? ###

1. Start a Docker Swarm.
2. Run some apps on your Swarm.
3. Change IP and Port to your Swarm.
4. Run the ContainerRetriever class.
5. Enjoy the output. :-)