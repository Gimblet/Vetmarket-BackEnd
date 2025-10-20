{
  description = "A very basic flake";

  inputs = {
    nixpkgs.url = "github:nixos/nixpkgs?ref=nixos-unstable";
  };

  outputs =
    { nixpkgs
    , ...
    }:
    let
      system = "x86_64-linux";
      pkgs = import nixpkgs {
        system = system;
      };
    in
    {

      devShells.${system}.default = pkgs.mkShell {
        packages = with pkgs; [
          jdk21
          maven
        ];

        SHELL = "${pkgs.zsh}/bin/zsh";

        JAVA_HOME = "${pkgs.jdk21}/lib/openjdk";
      };

    };
}
